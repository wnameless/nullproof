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

import java.util.Date;

import com.github.wnameless.nullproof.annotation.AcceptNull;
import com.github.wnameless.nullproof.annotation.Argument;
import com.github.wnameless.nullproof.annotation.RejectNull;

@RejectNull({ @Argument(type = String.class, message = "Oop! at $TYPE"),
    @Argument(type = Integer.class, ignore = true) })
@AcceptNull("barDouble")
public class AnnotatedFoo1 extends Foo {

  @Override
  public void barString(String s) {}

  @Override
  public void barInteger(Integer i) {}

  @RejectNull
  public void barInteger2(Integer i) {}

  @Override
  public void barDouble(Double d) {}

  @AcceptNull
  public void barByte(Byte b) {}

  public void barFloat(Float f) {}

  @RejectNull(@Argument(type = Long.class, ignore = true))
  public void barLong(Long l) {}

  @RejectNull(@Argument(type = Number.class, message = "Noop!"))
  public void barNumber(Number n) {}

  @RejectNull
  @AcceptNull
  public void barDate(Date d) {}

}
