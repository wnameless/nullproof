package com.github.wnameless.nullproof.aspectj;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.wnameless.nullproof.annotation.AcceptNull;
import com.github.wnameless.nullproof.annotation.Argument;
import com.github.wnameless.nullproof.annotation.RejectNull;

@Aspect
public class NullProofAspect {

  private static final int REGULAR_ERROR = -1;
  private static final int NO_ERROR = Integer.MIN_VALUE;
  private static final Argument[] emptyArgAnnotAry = new Argument[0];

  @Pointcut("within(@com.github.wnameless.nullproof.annotation.RejectNull *)")
  public void beanAnnotatedWithAtRejectNull() {}

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Pointcut("execution(public boolean equals(Object))")
  public void equalsMethod() {}

  @Pointcut("beanAnnotatedWithAtRejectNull() && publicMethod() && !equalsMethod()")
  public
      void publicMethodInsideAClassMarkedWithAtRejectNull() {}

  @Before("publicMethodInsideAClassMarkedWithAtRejectNull()")
  public void rejectNull(JoinPoint jointPoint) {
    MethodSignature sig = (MethodSignature) jointPoint.getSignature();
    Method m = sig.getMethod();
    Class<?> klass = m.getDeclaringClass();
    Object[] args = jointPoint.getArgs();

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
  }

  private String buildSuffix(Method m) {
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

  private void preventNulls(Method m, Object[] args, Argument[] arguments) {
    Class<?>[] argTypes = m.getParameterTypes();

    for (int i = 0; i < argTypes.length; i++) {
      Object arg = args[i];
      Class<?> type = argTypes[i];
      int errorType = checkErrorType(arguments, arg, type);
      if (errorType == NO_ERROR)
        continue;
      else if (errorType == REGULAR_ERROR)
        throw new NullPointerException("Parameter<" + type.getSimpleName()
            + "> can't be null" + buildSuffix(m));
      else
        throw new NullPointerException(arguments[errorType].message()
            + buildSuffix(m));
    }
  }

  private boolean notFoundIn(String[] nullables, String methodName) {
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
