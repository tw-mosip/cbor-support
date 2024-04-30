plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.6.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.16.0")
    implementation("org.json:json:20090211")
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation("co.nstant.in:cbor:0.9")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}
