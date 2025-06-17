# Module `simple-jwt-authzero`

## Introduction

The `simple-jwt-authzero` module is an implementation of module `simple-jwt-facade` which uses the third-party library `com.auth0:java-jwt`. By using this module can help you use JWT to help you in your application.

## Prerequisites

This whole `JDevKit` is developed by **JDK 17**, which means you have to use JDK 17 for better experience.

## Installation

> This module has already imported `simple-jwt-facade`, there is no need to import it again.

### If you are using `Maven`

It is quite simple to install this module by `Maven`. The only thing you need to do is find your `pom.xml` file in the project, then find the `<dependencies>` node in the `<project>` node, and add the following codes to `<dependencies>` node:

```xml
<dependency>
	<groupId>cn.org.codecrafters</groupId>
    <artifactId>simple-jwt-authzero</artifactId>
    <version>${simple-jwt-authzero.version}</version>
</dependency>
```

And run `mvn dependency:get` in your project root folder(i.e., if your `pom.xml` is located at `/path/to/your/project/pom.xml`, then your current work folder should be `/path/to/your/project`), then `Maven` will automatically download the `jar` archive from `Maven Central Repository`. This could be **MUCH EASIER** if you are using IDE(i.e., IntelliJ IDEA), the only thing you need to do is click the refresh button of `Maven`.

If you are restricted using the Internet, and have to make `Maven` offline, you could follow the following steps.

1. Download the `jar` file from any place you can get and transfer the `jar` files to your work computer.
2. Move the `jar` files to your local `Maven` Repository as the path of `/path/to/maven_local_repo/cn/org/codecrafters/simple-jwt-facade/`.

### If you are using `Gradle`

Add this module to your project with `Gradle` is much easier than doing so with `Maven`.

Find `build.gradle` in the needed project, and add the following code to the `dependencies` closure in the build script:

```groovy
implementation 'cn.org.codecrafters:simple-jwt-authzero:${simple-jwt-authzero.version}'
```

### If you are not using `Maven` or `Gradle`

1. Download the `jar` file from the Internet.
2. Create a folder in your project and name it as a name you like(i.e., for me, I prefer `vendor`).
3. Put the `jar` file to the folder you just created in Step 2.
4. Add this folder to your project `classpath`.

## Use the `AuthzeroTokenResolver`

We have implemented `TokenResolver` to make sure you can add JWT to your Java application as soon as possible. All you need to do is to create an instance of `com.onixbyte.simplejwt.authzero.AuthzeroTokenResolver` and other operations to JWT could follow our instruction in [`simple-jwt-facade`](../simple-jwt-facade/README.md).

## Contact

If you have any suggestions, ideas, don't hesitate contacting us via [GitHub Issues](https://github.com/CodeCraftersCN/jdevkit/issues/new) or [Discord Community](https://discord.gg/NQK9tjcBB8). 

If you face any bugs while using our library and you are able to fix any bugs in our library, we would be happy to accept pull requests from you on [GitHub](https://github.com/CodeCraftersCN/jdevkit/compare).
