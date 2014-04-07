package com.github.wnameless.nullproof;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.common.testing.NullPointerTester;

public class NullProofAspectTest {

  Foo foo;
  AnnotatedFoo1 annFoo1;

  // @Before
  public void setUp() throws Exception {
    foo = new Foo();
    annFoo1 = new AnnotatedFoo1();
  }

  // @Test
  public void aspectJIsNotWorkingWithoutRejectNullAnnotation() {
    new NotAnnotatedFoo().bar(null);
  }

  // @Test
  public void testNormalException() {
    try {
      foo.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage()
          .startsWith("Parameter<String> is not nullable"));
    }
  }

  // @Test
  public void testEqulasWithNull() {
    foo.equals((Object) null);
  }

  // @Test(expected = NullPointerException.class)
  public void testOverloadedEqulasWithNull() {
    foo.equals((Integer) null);
  }

  // @Test
  public void nullProofIsNotAffectPrivateMethods() {
    foo.barString("");
  }

  // @Test
  public void allPublicMethodNPETest() throws Exception {
    new NullPointerTester().ignore(
        foo.getClass().getMethod("equals", Object.class))
        .testAllPublicInstanceMethods(foo);
  }

  // @Test
  public void globalRejectNullExceptionMessageTest() {
    try {
      annFoo1.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Oop!"));
    }
  }

  // @Test
  public void globalRejectNullIngoreTest() {
    annFoo1.barInteger(null);
  }

  // @Test
  public void globalAccectNullIngoreTest() {
    annFoo1.barDouble(null);
  }

  // @Test
  public void localAccectNullIngoreTest() {
    annFoo1.barByte(null);
  }

  // @Test(expected = NullPointerException.class)
  public void normalRejectNullTest() {
    annFoo1.barFloat(null);
  }

  // @Test
  public void localRejectNullIngoreTest() {
    annFoo1.barLong(null);
  }

  // @Test
  public void localRejectNullExceptionMessageTest() {
    try {
      annFoo1.barNumber(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Noop!"));
    }
  }

  // @Test(expected = NullPointerException.class)
  public void localRejectNullOverrideTest() {
    annFoo1.barInteger2(null);
  }

  // @Test
  public void acceptNullIsHigherThanRejectNullTest() {
    annFoo1.barDate(null);
  }

  // @Test(expected = NullPointerException.class)
  public void testRejectNullWithoutAcceptNullOnClass() {
    AnnotatedFoo2 annFoo2 = new AnnotatedFoo2();
    annFoo2.barFloat(null);
  }

  // @Test
  public void testOnlyAcceptNullOnClass() {
    AnnotatedFoo3 annFoo3 = new AnnotatedFoo3();
    annFoo3.barInteger(null);
    annFoo3.barDouble(null);
  }

}
