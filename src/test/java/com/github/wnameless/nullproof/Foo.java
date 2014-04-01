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
package com.github.wnameless.nullproof;

import static com.github.wnameless.nullproof.NullProof.nullProof;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.TypeLiteral;

public class Foo {

  public Foo(Map<String, Integer> map) {}

  public Foo(Integer i) {}

  public Foo() {}

  public void method1(String s) {
    method2(null);
  }

  private void method2(String s) {}

  public void method3(String s) {
    method2(null);
  }

  public void method4(String s) {
    method2(null);
  }

  public static void main(String[] arg) {
    nullProof(Foo.class);
    nullProof(Foo.class, 1);

    Foo foo =
        new NullProof.Constructor<Foo>(Foo.class)
            .forType(new TypeLiteral<Map<String, Integer>>() {})
            .addArgument(new HashMap<String, Integer>()).make();

    foo.method1(null);
  }

}
