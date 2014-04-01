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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * {@link Argument} can be only used in {@link RejectNull} annotation. It
 * describes the detail actions while any null object is found by
 * {@link RejectNull}.
 *
 */
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, METHOD })
public @interface Argument {

  /**
   * The target Class of arguments.
   * 
   * @return any Class
   */
  Class<?> type();

  /**
   * The exception message is used while a NullPointerException is raised on
   * null arguments of target Class.
   * 
   * @return a String
   */
  String message() default "";

  /**
   * Option to suppress the NullPointerException of target Class.
   * 
   * @return true if the NullPointerException of target Class is ignored, false
   *         otherwise
   */
  boolean ignore() default false;

}
