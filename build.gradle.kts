plugins {
  `maven-publish`
  kotlin("jvm") version "1.3.71"
  kotlin("plugin.serialization") version "1.3.71"
}

group = "com.github.waahoo"
version = "0.0.1"

repositories {
  mavenCentral()
}

dependencies {
  val coroutineVer = "1.3.5"
  val serializationVer = "0.20.0"
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVer")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVer")
  implementation("com.squareup.okhttp3:okhttp:4.4.1")
  implementation("com.squareup.okhttp3:logging-interceptor:4.4.1")
  implementation("com.squareup.okhttp3:okhttp-urlconnection:4.4.1")
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}

val kotlinSourcesJar by tasks

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["kotlin"])
      artifact(kotlinSourcesJar)
    }
  }
}