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

import static com.github.wnameless.nullproof.NullProof.nullProof;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.NullPointerTester;
import com.google.inject.CreationException;
import com.google.inject.TypeLiteral;

public class NullProofTest {

  Foo foo;
  AnnotatedFoo annFoo;

  @Before
  public void setUp() throws Exception {
    foo = nullProof(Foo.class);
    annFoo = nullProof(AnnotatedFoo.class);
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
    nullProof(Foo.class, 1);
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
      annFoo.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Oop!"));
    }
  }

  @Test
  public void globalRejectNullIngoreTest() {
    annFoo.barInteger(null);
  }

  @Test
  public void globalAccectNullIngoreTest() {
    annFoo.barDouble(null);
  }

  @Test
  public void localAccectNullIngoreTest() {
    annFoo.barByte(null);
  }

  @Test(expected = NullPointerException.class)
  public void normalRejectNullTest() {
    annFoo.barFloat(null);
  }

  @Test
  public void localRejectNullIngoreTest() {
    annFoo.barLong(null);
  }

  @Test
  public void localRejectNullExceptionMessageTest() {
    try {
      annFoo.barNumber(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Noop!"));
    }
  }

  @Test(expected = NullPointerException.class)
  public void localRejectNullOverrideTest() {
    annFoo.barInteger2(null);
  }

  @Test
  public void acceptNullIsHigherThanRejectNullTest() {
    annFoo.barDate(null);
  }

}
