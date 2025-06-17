/*
 * Copyright (C) 2024-2025 OnixByte.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.net.URI

plugins {
    java
    id("java-library")
    id("maven-publish")
    id("signing")
}

val artefactVersion: String by project
val projectUrl: String by project
val projectGithubUrl: String by project
val licenseName: String by project
val licenseUrl: String by project

group = "com.onixbyte"
version = artefactVersion

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    exclude("logback.xml")
}

dependencies {
    compileOnly(libs.slf4j)
    implementation(libs.logback)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("devkitUtils") {
            groupId = group.toString()
            artifactId = "devkit-utils"
            version = artefactVersion

            pom {
                name = "OnixByte Common Toolbox"
                description = "The utils module of JDevKit."
                url = projectUrl

                licenses {
                    license {
                        name = licenseName
                        url = licenseUrl
                    }
                }

                scm {
                    connection = "scm:git:git://github.com:onixbyte/java-dev-kit.git"
                    developerConnection = "scm:git:git://github.com:onixbyte/java-dev-kit.git"
                    url = projectGithubUrl
                }

                developers {
                    developer {
                        id = "zihluwang"
                        name = "Zihlu Wang"
                        email = "really@zihlu.wang"
                        timezone = "Asia/Hong_Kong"
                    }

                    developer {
                        id = "siujamo"
                        name = "Siu Jam'o"
                        email = "jamo.siu@outlook.com"
                        timezone = "Asia/Shanghai"
                    }
                }
            }

            from(components["java"])

            signing {
                sign(publishing.publications["devkitUtils"])
            }
        }

        repositories {
            maven {
                name = "sonatypeNexus"
                url = URI(providers.gradleProperty("repo.maven-central.host").get())
                credentials {
                    username = providers.gradleProperty("repo.maven-central.username").get()
                    password = providers.gradleProperty("repo.maven-central.password").get()
                }
            }
        }
    }
}
