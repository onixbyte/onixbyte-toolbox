# Tuple

## Introduction

The `tuple` module provides simple and efficient implementations of mutable and immutable bi-tuples and tri-tuples. These tuples allow you to group two or three values together without creating custom classes, supporting convenient usage in various programming scenarios.

## Features

- Immutable and mutable versions of bi-tuples (pairs) and tri-tuples (triplets);
- Factory method of() for easy instantiation of all tuple types;
- Simple, lightweight implementation compatible with standard Java usage;
- Clear distinction between mutable and immutable tuples for flexibility.

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.onixbyte</groupId>
    <artifactId>tuple</artifactId>
    <version>$artefactVersion</version>
</dependency>
```

### Gradle

#### Version Catalogue

Add the following codes to you `gradle/libs.versions.toml`:

```toml
[version]
onixbyteToolbox = "$artefactVersion"

[libraries]
onixbyteToolbox-tuple = { group = "com.onixbyte", name = "tuple", version.ref = "onixbyteToolbox" }
```

Then add the following codes to your `build.gradle.kts` or `build.gradle` dependencies block:

```kotlin
implementation(libs.onixbyteToolbox.tuple)
```

```groovy
implementation libs.onixbyteToolbox.tuple
```

#### Kotlin DSL

Add the following line to your `build.gradle.kts` dependencies block:

```kotlin
implementation("com.onixbyte:tuple:$artefactVersion")
```

#### Groovy DSL

Add the following line to your `build.gradle` dependencies block:

```groovy
implementation 'com.onixbyte:tuple:${artefactVersion}'
```

## Dependencies

This module has no external dependencies other than the standard Java SDK.

## Acknowledgement

Thanks to all contributors who helped develop this module, improving its design, performance, and documentation over time.
