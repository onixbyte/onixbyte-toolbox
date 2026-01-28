/*
 * Copyright (c) 2024-2026 OnixByte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.net.URI

plugins {
    id("java-platform")
    id("maven-publish")
    id("signing")
}

val artefactVersion: String by project
val projectUrl: String by project
val projectGithubUrl: String by project
val licenseName: String by project
val licenseUrl: String by project

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        api("com.onixbyte:common-toolbox:$artefactVersion")
        api("com.onixbyte:identity-generator:$artefactVersion")
        api("com.onixbyte:crypto-toolbox:$artefactVersion")
        api("com.onixbyte:math-toolbox:$artefactVersion")
        api("com.onixbyte:tuple:$artefactVersion")
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalogue") {
            groupId = group.toString()
            artifactId = "version-catalogue"
            version = artefactVersion

            pom {
                name = "OnixByte Version Catalogue"
                description = "OnixByte DevKit BOM is designed to manage dependency versions centrally."
                url = projectUrl

                licenses {
                    license {
                        name = licenseName
                        url = licenseUrl
                    }
                }

                scm {
                    connection = "scm:git:git://github.com:onixbyte/onixbyte-toolbox.git"
                    developerConnection = "scm:git:git://github.com:onixbyte/onixbyte-toolbox.git"
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

            from(components["javaPlatform"])

            signing {
                sign(publishing.publications["versionCatalogue"])
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
