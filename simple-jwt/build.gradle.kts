plugins {
    id("java")
}

val artefactVersion: String by project

group = "com.onixbyte"
version = artefactVersion

repositories {
    mavenCentral()
}

dependencies {
    val jacksonVersion: String by project
    implementation(project(":devkit-core"))
    implementation(project(":devkit-utils"))
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}