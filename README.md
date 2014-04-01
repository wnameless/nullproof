nullproof
=============
A Java nullproof object constructor by Guice AOP

#Quick Start
```java
import static com.github.wnameless.nullproof.NullProof.nullProof;
```
Class with 0-argument constructor
```java
Foo foo = nullProof(Foo.class);
foo.bar(null); // throws NullPointerException
```

Class with single implementation arguments constructor
```java
Foo foo = nullProof(Foo.class, 123, "abc");
foo.bar(null); // throws NullPointerException
```

Class with generic and upcasting arguments constructor
```java
Foo foo = new NullProof.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {})
        .addArgument(new HashMap<String, Integer>()).make();
foo.bar(null); // throws NullPointerException
```
