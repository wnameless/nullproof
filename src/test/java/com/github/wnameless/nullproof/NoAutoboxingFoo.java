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

public class NoAutoboxingFoo {

  NoAutoboxingFoo(byte b) {}

  NoAutoboxingFoo(Byte b) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(short s) {}

  NoAutoboxingFoo(Short s) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(int i) {}

  NoAutoboxingFoo(Integer i) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(long l) {}

  NoAutoboxingFoo(Long l) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(float f) {}

  NoAutoboxingFoo(Float f) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(double d) {}

  NoAutoboxingFoo(Double d) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(boolean b) {}

  NoAutoboxingFoo(Boolean b) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(char c) {}

  NoAutoboxingFoo(Character c) {
    throw new IllegalArgumentException();
  }

}
