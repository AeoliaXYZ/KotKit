import org.jetbrains.gradle.ext.*

plugins {
  kotlin("jvm") version "2.2.0"
  kotlin("plugin.serialization") version "2.2.0"
  kotlin("kapt") version "2.2.0"

  id("com.gradleup.shadow") version "8.3.0"
  id("xyz.jpenilla.run-paper") version "2.3.1"
  id("xyz.jpenilla.run-velocity") version "2.3.1"

  id("eclipse")
  id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
}

group = "xyz.aeolia"
version = "1.0.1"

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
    name = "spigotmc-repo"
  }
  maven("https://oss.sonatype.org/content/repositories/snapshots") {
    name = "sonatype-oss-snapshots"
  }
  maven("https://repo.papermc.io/repository/maven-public/") {
    name = "papermc"
  }
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
  compileOnly("net.md-5:bungeecord-api:1.21-R0.1-SNAPSHOT")

  compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
}

kotlin {
  jvmToolchain(21)
}

tasks {
  runServer {
    minecraftVersion("1.21")
  }
  runVelocity {
    velocityVersion("3.4.0-SNAPSHOT") // must match dependency version
  }
  build {
    dependsOn(shadowJar)
  }
  processResources {
    val props = mapOf("version" to project.version)
    inputs.properties(props)
    filteringCharset = "UTF-8"

    filesMatching(listOf("plugin.yml", "**/*.yml", "**/*.txt")) {
      expand(props)
      filter { it.replace("%%VER", project.version.toString()) }
    }
  }
}

val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")

val generateTemplates = tasks.register<Copy>("generateTemplates") {
  val props = mapOf("version" to project.version)
  inputs.properties(props)

  from(templateSource)
  into(templateDest)
  expand(props)
}

tasks.processResources {
  val props = mapOf("version" to project.version)

  inputs.properties(props)
  filteringCharset = "UTF-8"

  filesMatching(listOf("plugin.yml", "bungee.yml", "velocity-plugin.json")) {
    expand(props)
  }
}

sourceSets {
  main {
    java.srcDir(templateDest)
  }
}

idea {
  project.settings.taskTriggers.afterSync(generateTemplates)
}

eclipse {
  synchronizationTasks(generateTemplates)
}
