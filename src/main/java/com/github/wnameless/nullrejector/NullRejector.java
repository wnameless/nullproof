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

import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * NullRejector is a Guice module designed for preventing null objects from
 * method calls by AOP programming.
 *
 */
public final class NullRejector extends AbstractModule {

  /**
   * Returns an instance of given Class which is prevented null objects by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          any non-final Class with non-private and zero-argument constructor
   * @return a null proof instance of given class
   */
  public static <E> E nullProof(Class<E> klass) {
    Injector injector = Guice.createInjector(new NullRejector());
    return injector.getInstance(klass);
  }

  @Override
  protected void configure() {
    bindInterceptor(any(), any(), new NullBlocker());
  }

}
