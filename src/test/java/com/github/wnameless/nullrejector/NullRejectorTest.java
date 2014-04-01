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

import static com.github.wnameless.nullrejector.NullProof.nullProof;

import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.NullPointerTester;

public class NullRejectorTest {

  Foo foo;

  @Before
  public void setUp() throws Exception {
    foo = nullProof(Foo.class);
  }

  @Test
  public void nullRejectorIsNotAffectPrivateMethods() {
    foo.method1("");
  }

  @Test
  public void allPublicMethodNPETest() throws Exception {
    new NullPointerTester().ignore(
        foo.getClass().getMethod("equals", Object.class))
        .testAllPublicInstanceMethods(foo);
  }

}
