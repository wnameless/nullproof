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

import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.TypeLiteral;

/**
 * 
 * {@link NullProof} protects any Object from null arguments. After the
 * enhancement, a NullPointerException will raise automatically if any null
 * argument is detected.
 *
 */
public final class NullProof {

  private NullProof() {}

  /**
   * 
   * {@link NullProof}.{@link Constructor} is a builder which is designed to
   * make a nullproof instance easier.
   * 
   * <P>
   * For example:<br>
   * 
   * <pre>
   * new NullProof.Constructor&lt;Foo&gt;(Foo.class)
   *     .forType(new TypeLiteral&lt;Map&lt;String, Integer&gt;&gt;() {})
   *     .addArgument(new HashMap&lt;String, Integer&gt;()).make();
   * </pre>
   *
   * @param <E>
   *          type of target Class
   */
  public static class Constructor<E> {

    private final Class<E> klass;
    private final List<TypeLiteral<?>> typeLiterals;
    private final List<Object> arguments;

    /**
     * Returns a {@link NullProof.Constructor}.
     * 
     * @param klass
     *          Class of target object
     */
    public Constructor(Class<E> klass) {
      this.klass = klass;
      typeLiterals = new ArrayList<TypeLiteral<?>>();
      arguments = new ArrayList<Object>();
    }

    public <T> ArgumentHolder<E> forType(TypeLiteral<T> typeLiteral) {
      typeLiterals.add(typeLiteral);
      return new ArgumentHolder<E>(this);
    }

    private <T> Constructor<E> addArgument(T argument) {
      arguments.add(argument);
      return this;
    }

    /**
     * Creates a nullproof instance of given Class.
     * 
     * @return a nullproof instance of given Class
     */
    public E create() {
      return of(klass,
          typeLiterals.toArray(new TypeLiteral[typeLiterals.size()]),
          arguments.toArray());
    }

  }

  public static class ArgumentHolder<E> {

    private final Constructor<E> builder;

    private ArgumentHolder(Constructor<E> builder) {
      this.builder = builder;
    }

    public <T> Constructor<E> addArgument(T argument) {
      return builder.addArgument(argument);
    }

  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          any non-final Class with non-private and zero-argument constructor
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass) {
    return Guice.createInjector(new NullRejector()).getInstance(klass);
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls. This method handles
   * complex generic and upcasting arguments of constructor.
   * 
   * @param klass
   *          Class of target object
   * @param types
   *          literal types of constructor parameters
   * @param args
   *          arguments of constructor
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final TypeLiteral<?>[] types,
      final Object... args) {
    return Guice.createInjector(new NullRejector(), new AbstractModule() {

      @SuppressWarnings("unchecked")
      @Override
      protected void configure() {
        for (int i = 0; i < types.length; i++) {
          bind((TypeLiteral<Object>) types[i]).toInstance((Object) args[i]);
        }

        Class<?>[] paramTypes = new Class<?>[types.length];
        for (int i = 0; i < types.length; i++) {
          paramTypes[i] = types[i].getRawType();
        }
        try {
          bind(klass).toConstructor(klass.getDeclaredConstructor(paramTypes));
        } catch (NoSuchMethodException e) {}
      }

    }).getInstance(klass);
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param b
   *          any byte
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final byte b) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(byte.class).toInstance(b);
          try {
            bind(klass).toConstructor(klass.getDeclaredConstructor(byte.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Byte.valueOf(b));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param b
   *          any Byte
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Byte b) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Byte>() {})
        .addArgument(b).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param s
   *          any short
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final short s) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(short.class).toInstance(s);
          try {
            bind(klass)
                .toConstructor(klass.getDeclaredConstructor(short.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Short.valueOf(s));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param s
   *          any Short
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Short s) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Short>() {})
        .addArgument(s).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param i
   *          any int
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final int i) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(int.class).toInstance(i);
          try {
            bind(klass).toConstructor(klass.getDeclaredConstructor(int.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Integer.valueOf(i));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param i
   *          any Integer
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Integer i) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Integer>() {})
        .addArgument(i).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param l
   *          any long
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final long l) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(long.class).toInstance(l);
          try {
            bind(klass).toConstructor(klass.getDeclaredConstructor(long.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Long.valueOf(l));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param l
   *          any Long
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Long l) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Long>() {})
        .addArgument(l).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param f
   *          any float
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final float f) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(float.class).toInstance(f);
          try {
            bind(klass)
                .toConstructor(klass.getDeclaredConstructor(float.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Float.valueOf(f));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param f
   *          any Float
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Float f) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Float>() {})
        .addArgument(f).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param d
   *          any double
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final double d) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(double.class).toInstance(d);
          try {
            bind(klass).toConstructor(
                klass.getDeclaredConstructor(double.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Double.valueOf(d));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param d
   *          any Double
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Double d) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Double>() {})
        .addArgument(d).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param b
   *          any boolean
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final boolean b) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(boolean.class).toInstance(b);
          try {
            bind(klass).toConstructor(
                klass.getDeclaredConstructor(boolean.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Boolean.valueOf(b));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param b
   *          any Boolean
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Boolean b) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Boolean>() {})
        .addArgument(b).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param c
   *          any char
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final char c) {
    try {
      return Guice.createInjector(new NullRejector(), new AbstractModule() {

        @Override
        protected void configure() {
          bind(char.class).toInstance(c);
          try {
            bind(klass).toConstructor(klass.getDeclaredConstructor(char.class));
          } catch (NoSuchMethodException e) {}
        }

      }).getInstance(klass);
    } catch (Exception e) {
      return of(klass, Character.valueOf(c));
    }
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls.
   * 
   * @param klass
   *          Class of target object
   * @param c
   *          any Character
   * @return a nullproof instance of given Class
   */
  public static <E> E of(Class<E> klass, Character c) {
    return new Constructor<E>(klass).forType(new TypeLiteral<Character>() {})
        .addArgument(c).create();
  }

  /**
   * Returns an instance of given Class which is prevented null arguments by
   * throwing NullPointerException from method calls. This method only handles
   * normal arguments of constructor without generic and upcasting.
   * 
   * @param klass
   *          Class of target object
   * @param args
   *          arguments of constructor
   * @return a nullproof instance of given Class
   */
  public static <E> E of(final Class<E> klass, final Object... args) {
    return Guice.createInjector(new NullRejector(), new AbstractModule() {

      @SuppressWarnings("unchecked")
      @Override
      protected void configure() {
        Class<?>[] paramTypes = new Class<?>[0];
        for (java.lang.reflect.Constructor<?> ct : klass
            .getDeclaredConstructors()) {
          boolean involkable = true;

          paramTypes = ct.getParameterTypes();
          if (paramTypes.length != args.length) {
            continue;
          } else {
            for (int i = 0; i < args.length; i++) {
              if (args[i] == null)
                continue;

              involkable = isConvertable(paramTypes[i], args[i].getClass());
              if (!involkable)
                break;
            }
          }

          if (involkable)
            break;
        }

        for (int i = 0; i < args.length; i++) {
          bind((Class<Object>) paramTypes[i]).toInstance((Object) args[i]);
        }
        try {
          bind(klass).toConstructor(klass.getDeclaredConstructor(paramTypes));
        } catch (NoSuchMethodException e) {}
      }

    }).getInstance(klass);
  }

  private static boolean isConvertable(Class<?> klass1, Class<?> klass2) {
    if (klass1 == byte.class)
      return klass2 == Byte.class;
    else if (klass1 == short.class)
      return klass2 == Short.class;
    else if (klass1 == int.class)
      return klass2 == Integer.class;
    else if (klass1 == long.class)
      return klass2 == Long.class;
    else if (klass1 == float.class)
      return klass2 == Float.class;
    else if (klass1 == double.class)
      return klass2 == Double.class;
    else if (klass1 == boolean.class)
      return klass2 == Boolean.class;
    else if (klass1 == char.class)
      return klass2 == Character.class;
    else
      return klass1.isAssignableFrom(klass2);
  }

}
