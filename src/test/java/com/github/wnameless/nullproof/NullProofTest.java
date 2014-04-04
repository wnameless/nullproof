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

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.NullPointerTester;
import com.google.inject.CreationException;
import com.google.inject.TypeLiteral;

public class NullProofTest {

  Foo foo;
  AnnotatedFoo1 annFoo1;

  @Before
  public void setUp() throws Exception {
    foo = NullProof.of(Foo.class);
    annFoo1 = NullProof.of(AnnotatedFoo1.class);
  }

  @Test
  public void testPrivateConstructor() throws Exception {
    Constructor<NullProof> c = NullProof.class.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(c.getModifiers()));
    c.setAccessible(true);
    c.newInstance();
  }

  @Test
  public void testNullProofWithNormalArgument() {
    NullProof.of(Foo.class, 1);
  }

  @Test(expected = CreationException.class)
  public void testNullProofWithNullArgument() {
    NullProof.of(Foo.class, (Integer) null);
  }

  @Test(expected = CreationException.class)
  public void testNullProofWithWrongArgumentNumber() {
    NullProof.of(Foo.class, 1, 2);
  }

  @Test(expected = CreationException.class)
  public void testNullProofWithWrongTypeArgument() {
    NullProof.of(Foo.class, new Date());
  }

  @Test
  public void testNullProofConstructor() {
    new NullProof.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {})
        .addArgument(new HashMap<String, Integer>()).make();
  }

  @Test(expected = CreationException.class)
  public void nullObjectCanNotBindTest() {
    new NullProof.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {}).addArgument(null)
        .make();
  }

  @Test
  public void testNormalException() {
    try {
      foo.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Parameter<String> can't be null"));
    }
  }

  @Test
  public void testEqulasWithNull() {
    foo.equals((Object) null);
  }

  @Test(expected = NullPointerException.class)
  public void testOverloadedEqulasWithNull() {
    foo.equals((Integer) null);
  }

  @Test
  public void nullProofIsNotAffectPrivateMethods() {
    foo.barString("");
  }

  @Test
  public void allPublicMethodNPETest() throws Exception {
    new NullPointerTester().ignore(
        foo.getClass().getMethod("equals", Object.class))
        .testAllPublicInstanceMethods(foo);
  }

  @Test
  public void globalRejectNullExceptionMessageTest() {
    try {
      annFoo1.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Oop! at String"));
    }
  }

  @Test
  public void globalRejectNullIngoreTest() {
    annFoo1.barInteger(null);
  }

  @Test
  public void globalAccectNullIngoreTest() {
    annFoo1.barDouble(null);
  }

  @Test
  public void localAccectNullIngoreTest() {
    annFoo1.barByte(null);
  }

  @Test(expected = NullPointerException.class)
  public void normalRejectNullTest() {
    annFoo1.barFloat(null);
  }

  @Test
  public void localRejectNullIngoreTest() {
    annFoo1.barLong(null);
  }

  @Test
  public void localRejectNullExceptionMessageTest() {
    try {
      annFoo1.barNumber(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Noop!"));
    }
  }

  @Test(expected = NullPointerException.class)
  public void localRejectNullOverrideTest() {
    annFoo1.barInteger2(null);
  }

  @Test
  public void acceptNullIsHigherThanRejectNullTest() {
    annFoo1.barDate(null);
  }

  @Test(expected = NullPointerException.class)
  public void testRejectNullWithoutAcceptNullOnClass() {
    AnnotatedFoo2 annFoo2 = NullProof.of(AnnotatedFoo2.class);
    annFoo2.barFloat(null);
  }

  @Test
  public void testOnlyAcceptNullOnClass() {
    AnnotatedFoo3 annFoo3 = NullProof.of(AnnotatedFoo3.class);
    annFoo3.barInteger(null);
    annFoo3.barDouble(null);
  }

  @Test
  public void testByteConstructor() {
    foo = NullProof.of(Foo.class, Byte.valueOf("0"));
  }

  @Test
  public void testShortConstructor() {
    foo = NullProof.of(Foo.class, Short.valueOf("0"));
  }

  @Test
  public void testIntConstructor() {
    foo = NullProof.of(Foo.class, Integer.valueOf("0"));
  }

  @Test
  public void testLongConstructor() {
    foo = NullProof.of(Foo.class, Long.valueOf("0"));
  }

  @Test
  public void testFloatConstructor() {
    foo = NullProof.of(Foo.class, Float.valueOf("0"));
  }

  @Test
  public void testDoubleConstructor() {
    foo = NullProof.of(Foo.class, Double.valueOf("0"));
  }

  @Test
  public void testBooleanConstructor() {
    foo = NullProof.of(Foo.class, Boolean.FALSE);
  }

  @Test
  public void testCharConstructor() {
    foo = NullProof.of(Foo.class, Character.valueOf('a'));
  }

}
