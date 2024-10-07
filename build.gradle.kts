import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21

group = "dev.luciano"
version = "1.0.0"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.web)
    implementation(libs.bundles.persistence)
    implementation(libs.bundles.observability)
    implementation(libs.bundles.security)
    implementation(libs.bundles.arrow)
    developmentOnly(libs.bundles.tools)
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.bundles.test.runtime)
}

dependencyManagement {
    imports {
        mavenBom(libs.arrow.bom.get().toString())
    }
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JVM_21
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Exec>("composeDown") {
    description = "Runs docker compose down command removing volumes"
    group = "docker"
    commandLine = listOf("docker", "compose", "down", "-v")
}