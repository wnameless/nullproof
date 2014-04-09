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
package com.github.wnameless.nullproof.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.wnameless.nullproof.NullBlocker;

@Aspect
public abstract class AbstractNullProofAspect {

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
