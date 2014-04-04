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
package com.github.wnameless.nullproof.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.wnameless.nullproof.NullProof;

/**
 * 
 * {@link RejectNull} must be used with {@link NullProof}. It automatically
 * checks all the arguments of method calls. It raises NullPointerException if
 * any null argument is found. This annotation should use with
 * {@link NullProof#of} or {@link NullProof#Constructor} for AOP purpose.
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface RejectNull {

  /**
   * The detail actions can be described by {@link Argument} annotations.
   * 
   * @return an Error array
   */
  Argument[] value() default {};

}
