package com.github.wnameless.nullproof.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.wnameless.nullproof.NullBlocker;

@Aspect
public class NullProofAspect {

  @Pointcut("within(@com.github.wnameless.nullproof.annotation.RejectNull *)")
  public void beanAnnotatedWithAtRejectNull() {}

  @Pointcut("execution(*.new(..))")
  public void constructor() {}

  @Pointcut("execution((@com.github.wnameless.nullproof.annotation.AcceptNull *).new(..))")
  public
      void constructorAnnotatedWithAtAcceptNull() {}

  @Pointcut("beanAnnotatedWithAtRejectNull() && constructor() && !constructorAnnotatedWithAtAcceptNull()")
  public
      void constructorInsideAClassMarkedWithAtRejectNull() {}

  @Before("constructorInsideAClassMarkedWithAtRejectNull()")
  public void rejectNullForConsructors(JoinPoint jointPoint) {
    ConstructorSignature sig = (ConstructorSignature) jointPoint.getSignature();
    NullBlocker.blockNulls(sig.getConstructor(), jointPoint.getArgs());
  }

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Pointcut("execution(public boolean equals(Object))")
  public void equalsMethod() {}

  @Pointcut("beanAnnotatedWithAtRejectNull() && publicMethod() && !equalsMethod() "
      + "&& !@annotation(com.github.wnameless.nullproof.annotation.AcceptNull)")
  public
      void publicMethodWithoutAtAcceptNullInsideAClassWithAtRejectNull() {}

  @Before("publicMethodWithoutAtAcceptNullInsideAClassWithAtRejectNull()")
  public void rejectNullForPublicMethods(JoinPoint jointPoint) {
    MethodSignature sig = (MethodSignature) jointPoint.getSignature();
    NullBlocker.blockNulls(sig.getMethod(), jointPoint.getArgs());
  }

}
