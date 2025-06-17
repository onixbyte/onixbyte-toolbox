# JWT Toolbox :: Spring Boot Starter

## Introduction

Module **JWT Toolbox :: Spring Boot Starter** is a lightweight and easy-to-use Spring Boot starter
module that provides seamless integration with JSON Web Token (JWT) authentication in Spring Boot
applications. With this starter, developers can easily configure and enable JWT-based authentication
for their APIs and web applications without the need for complex manual setup. It simplifies the
process of generating and validating JWTs, making it effortless to secure endpoints and implement
token-based authentication in Spring Boot projects. The module is designed to be flexible, allowing
developers to customize various aspects of JWT authentication to suit their specific requirements.
Overall, `jwt-toolbox-spring-boot-starter` is a convenient solution for adding secure and efficient JWT authentication to Spring Boot applications.

## Prerequisites

- This whole `JDevKit` is developed by **JDK 17**, which means you have to use JDK 17 for better experience.
- You also need an implementation of any `simple-jwt-$implementation` module to make sure you can create an instance of `TokenResolver`.

### Known Implementation

> If you implemented one on your own, please contact us to add your artifact to this list.

- [`cn.org.codecrafters:simple-jwt-authzero`](../simple-jwt-authzero/README.md)
- [`cn.org.codecrafters:simple-jwt-jjwt`](../simple-jwt-jjwt/README.md)

## Installation

### If you are using `Maven`

It is quite simple to install this module by `Maven`. The only thing you need to do is find your `pom.xml` file in the project, then find the `<dependencies>` node in the `<project>` node, and add the following codes to `<dependencies>` node:

```xml
<dependency>
	<groupId>${implementation-builder-group-id}</groupId>
    <artifactId>simple-jwt-${any-implementation}</artifactId>
    <version>${simple-jwt-${any-implementation}.version}</version>
</dependency>
<dependency>
	<groupId>com.onixbyte</groupId>
    <artifactId>simple-jwt-spring-boot-starter</artifactId>
    <version>${simple-jwt-spring-boot-starter.version}</version>
</dependency>
```

And run `mvn dependency:get` in your project root folder(i.e., if your `pom.xml` is located at `/path/to/your/project/pom.xml`, then your current work folder should be `/path/to/your/project`), then `Maven` will automatically download the `jar` archive from `Maven Central Repository`. This could be **MUCH EASIER** if you are using IDE(i.e., IntelliJ IDEA), the only thing you need to do is click the refresh button of `Maven`.

If you are restricted using the Internet, and have to make `Maven` offline, you could follow the following steps.

1. Download the `jar` file from any place you can get and transfer the `jar` files to your work computer.
2. Move the `jar` files to your local `Maven` Repository as the path of `/path/to/maven_local_repo/cn/org/codecrafters/simple-jwt-spring-boot-starter/` and `/path/to/maven_local_repo/${implementation-builder-group-seperated-by-system-seperator}/${implementation_artifact_id}`.

### If you are using `Gradle`

Add this module to your project with `Gradle` is much easier than doing so with `Maven`.

Find `build.gradle` in the needed project, and add the following code to the `dependencies` closure in the build script:

```groovy
implementation '${implementation-builder-group-id}:simple-jwt-${any-implementation}:${simple-jwt-${any-implementation}.version}'
implementation 'com.onixbyte:simple-jwt-spring-boot-starter:${simple-jwt-spring-boot-starter.version}'
```

### If you are not using `Maven` or `Gradle`

1. Download the `jar` file from the Internet.
2. Create a folder in your project and name it as a name you like(i.e., for me, I prefer `vendor`).
3. Put the `jar` file to the folder you just created in Step 2.
4. Add this folder to your project `classpath`.

## Configuration

### Configuration for a customized JWT ID Creator

We need a `GuidCreator` instance to create JWT ID, though we did implemented a simple `GuidCreator`, but you can still customize it.

First, please implement the `com.onixbyte.identitygenerator.IdentityGenerator` interface based on your own rules for generating JWT IDs.

Then, add the instance of your own guid creator to spring container, whose name is `jtiCreator`.

Here is a simple example which uses class `Random` to create guid.

```java
@Bean
public GuidCreator<?> jtiCreator() {
    return new GuidCreator<Long>() {
        private final Random random = new Random();

        @Override
        public Long nextId() {
            return random.nextLong();
        }
    };
}
```

### `TokenResolver` Configuration

| Property Name                        | Type             | Description                                            |
| ------------------------------------ | ---------------- | ------------------------------------------------------ |
| `code-crafters.simple-jwt.algorithm` | `TokenAlgorithm` | The algorithm used for JWT generation and validation.  |
| `code-crafters.simple-jwt.issuer`    | `String`         | The issuer value to be included in the generated JWT.  |
| `code-crafters.simple-jwt.secret`    | `String`         | The secret key used for JWT generation and validation. |

## Contact

If you have any suggestions, ideas, don't hesitate contacting us via [GitHub Issues](https://github.com/CodeCraftersCN/jdevkit/issues/new) or [Discord Community](https://discord.gg/NQK9tjcBB8). 

If you face any bugs while using our library and you are able to fix any bugs in our library, we would be happy to accept pull requests from you on [GitHub](https://github.com/CodeCraftersCN/jdevkit/compare).
