plugins {
    val pluginsVersions = object {
        val kotlin = "1.5.31"
        val springBoot = "2.5.6"
    }
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.noarg") version pluginsVersions.kotlin
    id("org.jetbrains.kotlin.plugin.allopen") version pluginsVersions.kotlin
    id("org.springframework.boot") version pluginsVersions.springBoot
}

apply(plugin = "org.springframework.boot")
apply(plugin = "kotlin-noarg")
apply(plugin = "kotlin-allopen")

springBoot {
    mainClass.set("it.twinsbrain.kata.game.configurations.Application")
}

dependencies {
    api(project(":domain"))
    api(project(":adapters"))

    implementation(platform("org.springframework.boot:spring-boot-dependencies:${depVersion("springBoot.version")}"))
    implementation("org.springframework.boot", "spring-boot-starter-web")
}

fun depVersion(key: String): String = project.properties[key].toString()
