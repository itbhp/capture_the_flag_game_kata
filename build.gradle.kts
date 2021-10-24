plugins {
    val pluginsVersions = object {
        val kotlin = "1.5.31"
        val ktLint = "10.2.0"
        val detekt = "1.18.1"
        val owasp = "6.4.1.1"
    }
    kotlin("jvm") version pluginsVersions.kotlin
    id("idea")
    id("org.jlleitschuh.gradle.ktlint") version pluginsVersions.ktLint
    id("io.gitlab.arturbosch.detekt") version pluginsVersions.detekt
    id("org.owasp.dependencycheck") version pluginsVersions.owasp
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    gradlePluginPortal()
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    apply(plugin = "java")
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.owasp.dependencycheck")

    val detekt by configurations.creating

    tasks.register<JavaExec>("detekt") {
        mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
        classpath = detekt

        val input = projectDir
        val config = "${rootProject.projectDir}/detekt-config.yml"
        val exclude = ".*/build/.*,.*/resources/.*"
        val params = listOf("-i", input, "-c", config, "-ex", exclude)

        args(params)
    }

    dependencies {
        testImplementation("org.mockito", "mockito-core", depVersion("mockito.version"))
        testImplementation("org.assertj", "assertj-core", depVersion("assertj.version"))
        testImplementation("org.junit.jupiter", "junit-jupiter-engine", depVersion("junit.version"))
        testImplementation("org.junit.jupiter", "junit-jupiter-params", depVersion("junit.version"))
        detekt("io.gitlab.arturbosch.detekt:detekt-cli:${depVersion("detekt.version")}")
    }

    tasks.test {
        useJUnitPlatform()
    }

    ktlint {
        version.set(depVersion("ktlint.pinterest.version"))
    }

    tasks.check {
        dependsOn("detekt")
    }
}

fun depVersion(key: String): String = project.properties[key].toString()
