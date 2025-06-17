# JWT Toolbox :: Auth0

## Introduction

**JWT Toolbox :: Auth0** module is an implementation of module JWT Toolbox, which uses third-party
library `com.auth0:java-jwt`. By using this module can help you use JWT to help you in
your application.

### If you are using `Maven`

It is quite simple to install this module by `Maven`. The only thing you need to do is find your
`pom.xml` file in the project, then find the `<dependencies>` node in the `<project>` node, and add
the following codes to `<dependencies>` node:

```xml

<dependency>
    <groupId>com.onixbyte</groupId>
    <artifactId>jwt-toolbox-auth0</artifactId>
    <version>${jwt-toolbox-auth0.version}</version>
</dependency>
```

And run `mvn dependency:get` in your project root folder(i.e., if your `pom.xml` is located at
`/path/to/your/project/pom.xml`, then your current work folder should be `/path/to/your/project`),
then `Maven` will automatically download the `jar` archive from `Maven Central Repository`.
This could be **MUCH EASIER** if you are using IDE(i.e., IntelliJ IDEA), the only thing you need to
do is click the refresh button of `Maven`.

If you are restricted using the Internet, and have to make `Maven` offline, you could follow the
following steps.

1. Download the `jar` file from any place you can get and transfer the `jar` files to your
   work computer.
2. Move the `jar` files to your local `Maven` Repository as the path of
   `/path/to/maven_local_repo/com/onixbyte/jwt-toolbox-auth0/`.

### If you are using `Gradle`

Add this module to your project with `Gradle` is much easier than doing so with `Maven`.

Find `build.gradle` in the needed project, and add the following code to the `dependencies` closure
in the build script:

```groovy
implementation 'com.onixbyte:jwt-toolbox-auth0:${jwt-toolbox-auth0.version}'
```

## Contact

If you have any suggestions, ideas, don't hesitate contacting us via
[GitHub Issues](https://github.com/onixbyte/onixbyte-toolbox/issues/new).

If you face any bugs while using our library and you are able to fix any bugs in our library, we
would be happy to accept pull requests from you on
[GitHub](https://github.com/onixbyte/onixbyte-toolbox/compare).
