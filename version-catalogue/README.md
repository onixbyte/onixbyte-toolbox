# Version Catalogue

The **Version Catalogue** (Bill of Materials) is a Maven POM file provided by OnixByte to manage
dependency versions for the **OnixByte Toolbox**. By incorporating this BOM into your build
configuration, you can ensure consistent versioning across all included dependencies without
needing to specify versions explicitly in your project files. Published with Gradle metadata,
this BOM supports both Maven and Gradle projects, and this document outlines how to integrate
and use it effectively in both ecosystems.

## Using in Maven

Add the `version-catalogue` to your `pom.xml` under `<dependencyManagement>`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.onixbyte</groupId>
            <artifactId>version-catalogue</artifactId>
            <version>3.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then reference any dependency built by OnixByte without a version.

## Using in Gradle

In your `build.gradle[.kts]`, apply the BOM using the `platform` dependency:

```groovy
dependencies {
    implementation platform('com.onixbyte:version-catalogue:3.0.0')
    implementation 'com.onixbyte:common-toolbox'
}
```

If you are using Kotlin DSL:

```kotlin
dependencies {
    implementation(platform("com.onixbyte:version-catalogue:3.0.0"))
    implementation("com.onixbyte:common-toolbox")
}
```

