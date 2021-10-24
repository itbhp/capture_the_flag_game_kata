plugins {
    kotlin("jvm") version "1.5.31"
    id("idea")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("org.owasp.dependencycheck") version "6.4.1.1"
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
            languageVersion.set(JavaLanguageVersion.of(11))
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
        testImplementation("org.mockito:mockito-core:4.0.0")
        testImplementation("org.assertj:assertj-core:3.21.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")

        detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.18.1")
    }

    tasks.test {
        useJUnitPlatform()
    }

    ktlint {
        version.set("0.42.1")
    }

    tasks.check {
        dependsOn("detekt")
    }
}
