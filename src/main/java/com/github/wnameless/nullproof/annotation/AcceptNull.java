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

import com.github.wnameless.nullproof.NullRejector;

/**
 * 
 * {@link AcceptNull} can be used to let {@link NullRejector} ignore certain
 * methods. It can be annotated both on Type and Method, but the value only
 * takes effect when it is on a Type, otherwise it ignores the annotated Method
 * directly.
 * 
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface AcceptNull {

  /**
   * Names of methods to be ignored. It takes effect only if it is annotated on
   * a Type not a Method.
   * 
   * @return a String array
   */
  String[] value() default {};

}
