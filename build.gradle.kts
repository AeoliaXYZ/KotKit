plugins {
  kotlin("jvm") version "2.2.0"
  kotlin("plugin.serialization") version "2.2.0"
  id("com.gradleup.shadow") version "8.3.0"
  id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "xyz.aeolia"
version = "1.0"

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
    name = "spigotmc-repo"
  }
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
}

tasks {
  runServer {
    minecraftVersion("1.21")
  }
  build {
    dependsOn("shadowJar")
  }
  processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
      expand(props)
    }
  }
}

kotlin {
  jvmToolchain(21)
}


tasks.processResources {
  val projectVer = project.version
  filesMatching(listOf("**/*.yml", "**/*.txt")) {
    filter {
      it.replace("%%VER", projectVer.toString())
    }
  }
}
