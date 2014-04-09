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

@RejectNull({ @Argument(type = String.class, message = "Wow!"),
    @Argument(type = Integer.class, ignore = true) })
public class AnnotatedConstructorFoo {

  public AnnotatedConstructorFoo(String s) {}

  public AnnotatedConstructorFoo(Integer s) {}

  @RejectNull(@Argument(type = Double.class, message = "Yay!"))
  public AnnotatedConstructorFoo(Double d) {}

  @RejectNull(@Argument(type = Float.class, ignore = true))
  public AnnotatedConstructorFoo(Float f) {}

  @AcceptNull
  @RejectNull
  public AnnotatedConstructorFoo(Date d) {}

}
