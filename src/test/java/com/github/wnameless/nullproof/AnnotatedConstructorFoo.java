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
