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
    id("java-platform")
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

dependencies {
    constraints {
        api("com.onixbyte:devkit-core:$artefactVersion")
        api("com.onixbyte:devkit-utils:$artefactVersion")
        api("com.onixbyte:guid:$artefactVersion")
        api("com.onixbyte:key-pair-loader:$artefactVersion")
        api("com.onixbyte:map-util-unsafe:$artefactVersion")
        api("com.onixbyte:num4j:$artefactVersion")
        api("com.onixbyte:simple-jwt-facade:$artefactVersion")
        api("com.onixbyte:simple-jwt-authzero:$artefactVersion")
        api("com.onixbyte:simple-jwt-spring-boot-starter:$artefactVersion")
        api("com.onixbyte:property-guard-spring-boot-starter:$artefactVersion")
        api("com.onixbyte:simple-serial-spring-boot-starter:$artefactVersion")
    }
}

publishing {
    publications {
        create<MavenPublication>("devkitBom") {
            groupId = group.toString()
            artifactId = "devkit-bom"
            version = artefactVersion

            pom {
                name = "DevKit BOM"
                description = "Using BOM could use unified OnixByte JDevKit."
                url = projectUrl

                licenses {
                    license {
                        name = licenseName
                        url = licenseUrl
                    }
                }

                scm {
                    connection = "scm:git:git://github.com:OnixByte/devkit-bom.git"
                    developerConnection = "scm:git:git://github.com:OnixByte/devkit-bom.git"
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

            print(components)

            from(components["javaPlatform"])

            signing {
                sign(publishing.publications["devkitBom"])
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