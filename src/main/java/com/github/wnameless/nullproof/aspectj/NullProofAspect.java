package com.github.wnameless.nullproof.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.wnameless.nullproof.NullBlocker;

@Aspect
public class NullProofAspect {

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
    NullBlocker.blockNulls(sig.getMethod(), jointPoint.getArgs());
  }

}
