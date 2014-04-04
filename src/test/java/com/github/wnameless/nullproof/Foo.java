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

import java.util.Map;

public class Foo {

  public Foo(byte b) {}

  public Foo(short s) {}

  public Foo(int i) {}

  public Foo(long l) {}

  public Foo(float f) {}

  public Foo(double d) {}

  public Foo(boolean b) {}

  public Foo(char c) {}

  public Foo(Map<String, Integer> map) {}

  public Foo(String s) {}

  public Foo() {}

  private void bar(String s) {}

  public void barString(String s) {
    bar(null);
  }

  public void barInteger(Integer i) {}

  public void barDouble(Double d) {}

  public boolean equals(Integer i) {
    return false;
  }

}
