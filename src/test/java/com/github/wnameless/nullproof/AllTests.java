package com.github.wnameless.nullproof;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NullProofAspectTest.class, NullProofTest.class })
public class AllTests {}
