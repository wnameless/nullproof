nullproof
=============
A Java null arguments proofed object constructor by Guice AOP

##Purpose
If you have a Class which conatains 20 methods and each method takes 2 arguments,
you probably need to repeat following line of code 40 times!!
```java
if(arg == null)
    throw new NullPointerException("Your error message");
```
With NullProof, you don't need to do it anymore.

#Quick Start
Class with 0-argument constructor
```java
Foo foo = NullProof.of(Foo.class);
foo.bar(null); // throws NullPointerException
```

Class with single implementation arguments constructor
```java
Foo foo = NullProof.of(Foo.class, 123, "abc");
foo.bar(null); // throws NullPointerException
```

Class with generic and upcasting arguments constructor
```java
Foo foo = new NullProof.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {})
        .addArgument(new HashMap<String, Integer>()).make();
foo.bar(null); // throws NullPointerException
```

#Feature
Annotation-Driven configuration:
```java
@RejectNull // On Type or Method.
@Argument   // Can only used within RejectNull
@AcceptNull // On Type or Method.
```

@RejectNull can be used to set up the default behavior of NullProof.
```java
@RejectNull({
    @Argument(type = String.class, message = "Oop!"),
    @Argument(type = Integer.class, ignore = true) })
```

@AcceptNull can be used to ignore certain methods.
```java
    @AcceptNull({ "bar1", "bar2" }) // Works on Type
    @AcceptNull // Works on Method
```

Descriptive default error message, the original line number is kept:
```java
java.lang.NullPointerException: Parameter<String> can't be null
	at com.github.wnameless.nullproof.Foo.bar(Foo.java:16)
        ...
```

#Best Practice
Using NullProof with static factory pattern to ensure every instance is prevented from null arguments.
```java
public Foo {
    public static Foo create() {
      return NullProof.of(Foo.class);
    }

    Foo () {} // Guice requires the class with at least a package-private(non-private) constuctor to do the AOP
    ...
}
```
