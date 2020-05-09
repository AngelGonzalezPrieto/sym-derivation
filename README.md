# sym-derivation

Library for symbolic computation in Java.

It allows symbolic exact derivation. It also provides utilities for parsing from prefix form as well as translation into infix form and equivalent Java code.

## Installation

In order to add Sym-Derivation to your project, you can do it via Maven or directly with a `jar package.

#### Using Maven.

Copy the following lines to your `pom.xml`file.

```xml
<dependency>
    <groupId>es.upm.etsisi</groupId>
    <artifactId>sym-derivation</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### As a `jar` package.

If you prefer to use the library without a dependency management tool, you must add the `jar` packaged version of Sym-Derivation to your project's classpath. For example, if you are using IntelliJ IDEA, copy the file to your project's directory, make right click on the `jar` file and select `Add as Library`.

You can find the `jar` packaged version of Sym-Derivation into the release section of GitHub.

You can also package your own `jar` file . To do that, clone the repository using `git clone git@github.com:AngelGonzalezPrieto/sym-derivation.git` and package it with `mvn package`.
