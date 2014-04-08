package com.github.wnameless.nullproof;

import com.github.wnameless.nullproof.annotation.AcceptNull;
import com.github.wnameless.nullproof.annotation.RejectNull;

@RejectNull
@AcceptNull("AcceptNullOnConstructorFoo")
public class AcceptNullOnConstructorFoo {

  public AcceptNullOnConstructorFoo(String s) {}

}
