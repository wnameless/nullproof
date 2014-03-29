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
package com.github.wnameless.nullrejector.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * RejectNull annotation can automatically check all arguments of method calls
 * and raise NullPointerException if any null object is found. This annotation
 * should use with NullRejector#nullProof for AOP purpose.
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface RejectNull {

  /**
   * The detail actions can be described by Error annotations.
   * 
   * @return an Error array
   */
  Error[] value() default {};

}
