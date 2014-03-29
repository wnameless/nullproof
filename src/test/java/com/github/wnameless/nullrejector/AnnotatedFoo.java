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

import com.github.wnameless.nullrejector.annotation.AcceptNull;
import com.github.wnameless.nullrejector.annotation.Argument;
import com.github.wnameless.nullrejector.annotation.RejectNull;

@RejectNull(@Argument(type = String.class, message = "Oop!", ignore = false))
@AcceptNull({ "bar1", "bar2" })
public class AnnotatedFoo {

  @RejectNull(@Argument(type = String.class))
  @AcceptNull
  public void bar(String s) {}

  public void bar1(String s) {}

  public void bar2(String s) {}

}
