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
package com.github.wnameless.nullrejector;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.github.wnameless.nullrejector.annotation.AcceptNull;
import com.github.wnameless.nullrejector.annotation.Error;
import com.github.wnameless.nullrejector.annotation.RejectNull;
import com.github.wnameless.nullrejector.annotation.ShowSuffix;

/**
 * 
 * NullBlocker is a Guice interceptor for raising a NullPointerException if any
 * null object is found on arguments of the called method.
 *
 */
public final class NullBlocker implements MethodInterceptor {

  private static final int REGULAR_ERROR = -1;
  private static final int NO_ERROR = Integer.MIN_VALUE;

  private static final Error[] emptyErrorAry = new Error[0];
  private static final String[] emptyStrAry = new String[0];

  public Object invoke(MethodInvocation invocation) throws Throwable {
    Method m = invocation.getMethod();
    Class<?> klass = m.getDeclaringClass();
    Class<?>[] argTypes = m.getParameterTypes();
    Object[] args = invocation.getArguments();
    String msgSuffix = buildSuffix(m);

    // Object#equals is always ignored
    if (argTypes.length == 1 && m.getName().equals("equals")
        && Object.class.equals(argTypes[0]))
      return invocation.proceed();

    AcceptNull methodAN = m.getAnnotation(AcceptNull.class);
    if (methodAN != null)
      return invocation.proceed();

    RejectNull methodPN = m.getAnnotation(RejectNull.class);
    ShowSuffix methodSS = m.getAnnotation(ShowSuffix.class);
    if (methodPN != null) {
      preventNulls(args, argTypes, methodPN.value(), msgSuffix,
          methodSS == null ? true : methodSS.value());
      return invocation.proceed();
    }

    AcceptNull classAN = klass.getAnnotation(AcceptNull.class);
    RejectNull classPN = klass.getAnnotation(RejectNull.class);
    ShowSuffix classSS =
        methodSS != null ? methodSS : klass.getAnnotation(ShowSuffix.class);
    if (classPN == null) {
      Error[] errors = emptyErrorAry;
      preventNulls(args, argTypes, errors, msgSuffix, true);
    } else {
      String[] exceptions = classAN == null ? emptyStrAry : classAN.value();
      if (notFoundIn(exceptions, m.getName()))
        preventNulls(args, argTypes, classPN.value(), msgSuffix,
            classSS == null ? true : classSS.value());
    }

    return invocation.proceed();
  }

  private String buildSuffix(Method m) {
    return "\n\tat " + m.getDeclaringClass().getName() + "." + m.getName()
        + "(" + m.getDeclaringClass().getSimpleName() + ".java:1)";
  }

  private void preventNulls(Object[] arguments, Class<?>[] argTypes,
      Error[] errors, String msgSuffix, boolean hasSuffix) {
    for (int i = 0; i < argTypes.length; i++) {
      Object arg = arguments[i];
      Class<?> type = argTypes[i];
      int errorType = checkErrorType(errors, arg, type);
      if (errorType == NO_ERROR)
        continue;
      else if (errorType == REGULAR_ERROR)
        throw new NullPointerException(hasSuffix ? msgSuffix : "");
      else
        throw new NullPointerException(errors[errorType].message()
            + (hasSuffix ? msgSuffix : ""));
    }
  }

  private boolean notFoundIn(String[] exceptions, String methodName) {
    for (String name : exceptions) {
      if (methodName.equals(name) || methodName.equals("equals"))
        return false;
    }
    return true;
  }

  private static int checkErrorType(Error[] errors, Object arg, Class<?> klass) {
    if (arg != null)
      return NO_ERROR;

    if (errors.length == 0)
      return REGULAR_ERROR;

    for (int i = 0; i < errors.length; i++) {
      Error e = errors[i];
      if (e.of().equals(klass)) {
        if (e.ignore())
          return NO_ERROR;
        else
          return i;
      }
    }

    return REGULAR_ERROR;
  }

}
