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

import com.github.wnameless.nullproof.annotation.RejectNull;

@RejectNull
public class Foo {

  public Foo(byte b, String s) {}

  public Foo(short s, String str) {}

  public Foo(int i, String s) {}

  public Foo(long l, String s) {}

  public Foo(float f, String s) {}

  public Foo(double d, String s) {}

  public Foo(boolean b, String s) {}

  public Foo(char c, String s) {}

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
