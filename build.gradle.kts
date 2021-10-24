plugins {
    kotlin("jvm") version "1.5.31"
    id("idea")
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    apply(plugin = "java")

    dependencies {
        testImplementation("org.mockito:mockito-core:4.0.0")
        testImplementation("org.assertj:assertj-core:3.21.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    }

    tasks.test {
        useJUnitPlatform()
    }
}