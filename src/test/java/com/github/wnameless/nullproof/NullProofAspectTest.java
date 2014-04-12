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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.NullPointerTester;

public class NullProofAspectTest {

  Foo foo;
  AnnotatedFoo1 annFoo1;

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    foo = new Foo();
    annFoo1 = new AnnotatedFoo1();
  }

  @Test
  public void testAspectJIsNotWorkingWithoutRejectNullAnnotation() {
    new NotAnnotatedFoo().bar(null);
  }

  @Test
  public void testConstuctorWithNullArgument() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    new Foo((String) null);
  }

  @Test
  public void testClassRNMessage() {
    try {
      new AnnotatedConstructorFoo((String) null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Wow!"));
    }
  }

  @Test
  public void testClassRNIgnore() {
    new AnnotatedConstructorFoo((Integer) null);
  }

  @Test
  public void testLocalRNMessage() {
    try {
      new AnnotatedConstructorFoo((Double) null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Yay!"));
    }
  }

  @Test
  public void testLocalRNIgnore() {
    new AnnotatedConstructorFoo((Float) null);
  }

  @Test
  public void testLocalAN() {
    new AnnotatedConstructorFoo((Date) null);
  }

  @Test
  public void testAcceptNullOnConstructor() {
    new AnnotatedFoo4(null);
  }

  @Test
  public void testNormalException() {
    try {
      foo.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage()
          .startsWith("Parameter<String> is not nullable"));
    }
  }

  @Test
  public void testEqulasWithNull() {
    foo.equals((Object) null);
  }

  @Test
  public void testOverloadedEqulasWithNull() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    foo.equals((Integer) null);
  }

  @Test
  public void testNullProofIsNotAffectPrivateMethods() {
    foo.barString("");
  }

  @Test
  public void testAllPublicMethodNPE() throws Exception {
    new NullPointerTester().ignore(
        foo.getClass().getMethod("equals", Object.class))
        .testAllPublicInstanceMethods(foo);
  }

  @Test
  public void testGlobalRejectNullExceptionMessage() {
    try {
      annFoo1.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Oop!"));
    }
  }

  @Test
  public void testgGobalRejectNullIngore() {
    annFoo1.barInteger(null);
  }

  @Test
  public void testGlobalAccectNullIngore() {
    annFoo1.barDouble(null);
  }

  @Test
  public void testLocalAccectNullIngore() {
    annFoo1.barByte(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNormalRejectNull() {
    annFoo1.barFloat(null);
  }

  @Test
  public void testLocalRejectNullIngore() {
    annFoo1.barLong(null);
  }

  @Test
  public void testLocalRejectNullExceptionMessage() {
    try {
      annFoo1.barNumber(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Noop!"));
    }
  }

  @Test
  public void testLocalRejectNullOverride() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    annFoo1.barInteger2(null);
  }

  @Test
  public void testAcceptNullIsHigherThanRejectNull() {
    annFoo1.barDate(null);
  }

  @Test
  public void testRejectNullWithoutAcceptNullOnClass() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    AnnotatedFoo2 annFoo2 = new AnnotatedFoo2();
    annFoo2.barFloat(null);
  }

  @Test
  public void testOnlyAcceptNullOnClass() {
    AnnotatedFoo3 annFoo3 = new AnnotatedFoo3();
    annFoo3.barInteger(null);
    annFoo3.barDouble(null);
  }

  @Test
  public void testOnStaticFactoryMethod() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    StaticFactoryFoo.newInstance(null);
  }

  @Test
  public void testAcceptNullOnStaticFactoryMethod() {
    StaticFactoryFoo.getInstance(null);
  }

  @Test
  public void testSingleRejectNull() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    new AnnotatedFoo5("", 1, null);
  }

  @Test
  public void testNotFoundInClassAcceptNullOnConstructor() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    new AnnotatedFoo6((String) null);
  }

  @Test
  public void testOnNoMatchClassAcceptNull() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    new AnnotatedFoo6("").bar1(1, null);
  }

  @Test
  public void testGlobalAcceptNull() {
    new AnnotatedFoo6("").bar2(null);
  }

  @Test
  public void testLocalAcceptNullOnConstructor() {
    new AnnotatedFoo6((Float) null);
  }

  @Test
  public void testOnInnerClass() {
    new InnerClassFoo();
  }

}
