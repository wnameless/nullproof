/**
 *
 * @author Wei-Ming Wu
 *
 *
 * Copyright 2014 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.github.wnameless.nullproof;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.github.wnameless.nullproof.annotation.AcceptNull;
import com.github.wnameless.nullproof.annotation.Argument;
import com.github.wnameless.nullproof.annotation.RejectNull;

/**
 * 
 * {@link NullBlocker} is a Guice MethodInterceptor which is designed to raise a
 * NullPointerException if any null argument is found during a method call.
 * 
 */
public final class NullBlocker implements MethodInterceptor {

  private static final int REGULAR_ERROR = -1;
  private static final int NO_ERROR = Integer.MIN_VALUE;
  private static final Argument[] emptyArgAnnotAry = new Argument[0];

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    blockNulls(invocation.getMethod(), invocation.getArguments());
    return invocation.proceed();
  }

  public static void blockNulls(Method m, Object[] args) {
    Class<?> klass = m.getDeclaringClass();
    Class<?>[] argTypes = m.getParameterTypes();

    // Object#equals is always ignored
    if (argTypes.length == 1 && m.getName().equals("equals")
        && Object.class.equals(argTypes[0]))
      return;

    AcceptNull methodAN = m.getAnnotation(AcceptNull.class);
    if (methodAN != null)
      return;

    RejectNull methodRN = m.getAnnotation(RejectNull.class);
    if (methodRN != null) {
      preventNulls(m, args, methodRN.value());
      return;
    }

    AcceptNull classAN = klass.getAnnotation(AcceptNull.class);
    RejectNull classRN = klass.getAnnotation(RejectNull.class);
    if (classAN == null || notFoundIn(classAN.value(), m.getName()))
      preventNulls(m, args,
          classRN == null ? emptyArgAnnotAry : classRN.value());

    return;
  }

  private static String buildSuffix(Method m) {
    Class<?>[] argTypes = m.getParameterTypes();
    Class<?> klass = m.getDeclaringClass();

    ClassPool pool = ClassPool.getDefault();
    int lineNum = 1;
    CtMethod method;
    try {
      CtClass cc = pool.get(klass.getName());
      CtClass[] ctClasses = new CtClass[argTypes.length];
      for (int i = 0; i < ctClasses.length; i++) {
        ctClasses[i] = pool.get(argTypes[i].getName());
      }
      method = cc.getDeclaredMethod(m.getName(), ctClasses);
      lineNum = method.getMethodInfo().getLineNumber(0) - 1;
    } catch (NotFoundException e) {}

    return "\n\tat " + klass.getName() + "." + m.getName() + "("
        + klass.getSimpleName() + ".java:" + lineNum + ")";
  }

  private static void
      preventNulls(Method m, Object[] args, Argument[] arguments) {
    Class<?>[] argTypes = m.getParameterTypes();

    for (int i = 0; i < argTypes.length; i++) {
      Object arg = args[i];
      Class<?> type = argTypes[i];
      int errorType = checkErrorType(arguments, arg, type);
      if (errorType == NO_ERROR)
        continue;
      else if (errorType == REGULAR_ERROR)
        throw new NullPointerException("Parameter<" + type.getSimpleName()
            + "> is not nullable" + buildSuffix(m));
      else
        throw new NullPointerException(arguments[errorType].message()
            + buildSuffix(m));
    }
  }

  private static boolean notFoundIn(String[] nullables, String methodName) {
    for (String name : nullables) {
      if (methodName.equals(name))
        return false;
    }
    return true;
  }

  private static int checkErrorType(Argument[] arguments, Object arg,
      Class<?> klass) {
    if (arg != null)
      return NO_ERROR;

    for (int i = 0; i < arguments.length; i++) {
      Argument a = arguments[i];
      if (a.type().equals(klass)) {
        if (a.ignore())
          return NO_ERROR;
        else
          return i;
      }
    }

    return REGULAR_ERROR;
  }

}
