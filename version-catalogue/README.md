# DevKit BOM

The devkit-bom (Bill of Materials) is a Maven POM file provided by OnixByte to manage dependency versions for the DevKit suite of libraries. By incorporating this BOM into your build configuration, you can ensure consistent versioning across all included dependencies without needing to specify versions explicitly in your project files. Published with Gradle metadata, this BOM supports both Maven and Gradle projects, and this document outlines how to integrate and use it effectively in both ecosystems.

## Using in Maven

Add the `devkit-bom` to your `pom.xml` under `<dependencyManagement>`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.onixbyte</groupId>
            <artifactId>devkit-bom</artifactId>
            <version>${devkit-version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then reference dependencies like `devkit-core` without a version.

## Using in Gradle

In your `build.gradle[.kts]`, apply the BOM using the `platform` dependency:

```groovy
dependencies {
    implementation platform('com.onixbyte:devkit-bom:2.0.0')
    implementation 'com.onixbyte:devkit-core'
    implementation 'com.onixbyte:devkit-utils'
}
```

If you are using Kotlin DSL:

```kotlin
dependencies {
    implementation(platform("com.onixbyte:devkit-bom:2.0.0"))
    implementation("com.onixbyte:devkit-core")
    implementation("com.onixbyte:devkit-utils")
}
```

