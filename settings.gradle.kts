rootProject.name = "spring-kotlin-accelerator"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Versions
            version("kotlin", "1.9.24")
            version("springBoot", "3.3.3")
            version("springDependencyManagement", "1.1.6")
            version("openApi", "2.6.0")
            version("arrow", "1.2.4")

            // Plugins
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("spring-boot", "org.springframework.boot").versionRef("springBoot")
            plugin("spring-dependency-management", "io.spring.dependency-management").versionRef("springDependencyManagement")

            // Bundles
            bundle(
                "web", listOf(
                    "spring-boot-starter-validation",
                    "spring-boot-starter-webflux",
                    "spring-boot-starter-graphql",
                    "jackson-module-kotlin",
                    "springdoc-openapi-webflux"
                )
            )

            bundle(
                "persistence", listOf(
                    "spring-boot-starter-data-r2dbc",
                    "r2dbc-postgresql"
                )
            )

            bundle(
                "observability", listOf(
                    "spring-boot-starter-actuator"
                )
            )

            bundle(
                "security", listOf(
                    "spring-boot-starter-oauth2-resource-server",
                    "spring-security-oauth2-jose"
                )
            )

            bundle(
                "test", listOf(
                    "spring-boot-starter-test",
                    "spring-graphql-test",
                    "spring-boot-testcontainers",
                    "reactor-test",
                    "kotlin-test-junit5",
                    "testcontainers-junit-jupiter"
                )
            )
            bundle("test-runtime", listOf("junit-platform-launcher"))

            bundle(
                "tools", listOf(
                    "spring-boot-devtools",
                    "spring-boot-docker-compose"
                )
            )

            bundle(
                "kotlin-coroutines", listOf(
                    "reactor-kotlin-extensions",
                    "kotlin-reflect",
                    "kotlinx-coroutines-reactor"
                )
            )

            bundle(
                "arrow", listOf(
                    "arrow-core",
                    "arrow-fx-coroutines"
                )
            )

            // Libraries
            library("reactor-kotlin-extensions", "io.projectreactor.kotlin", "reactor-kotlin-extensions").withoutVersion()
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").withoutVersion()
            library("kotlinx-coroutines-reactor", "org.jetbrains.kotlinx", "kotlinx-coroutines-reactor").withoutVersion()

            library("spring-boot-starter-validation", "org.springframework.boot", "spring-boot-starter-validation").withoutVersion()
            library("spring-boot-starter-webflux", "org.springframework.boot", "spring-boot-starter-webflux").withoutVersion()
            library("spring-boot-starter-graphql", "org.springframework.boot", "spring-boot-starter-graphql").withoutVersion()
            library("jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").withoutVersion()
            library("springdoc-openapi-webflux", "org.springdoc", "springdoc-openapi-starter-webflux-ui").versionRef("openApi")

            library("spring-boot-starter-data-r2dbc", "org.springframework.boot", "spring-boot-starter-data-r2dbc").withoutVersion()
            library("r2dbc-postgresql", "org.postgresql", "r2dbc-postgresql").withoutVersion()

            library("spring-boot-starter-actuator", "org.springframework.boot", "spring-boot-starter-actuator").withoutVersion()

            library(
                "spring-boot-starter-oauth2-resource-server",
                "org.springframework.boot",
                "spring-boot-starter-oauth2-resource-server"
            ).withoutVersion()
            library("spring-security-oauth2-jose", "org.springframework.security", "spring-security-oauth2-jose").withoutVersion()

            library("arrow-bom", "io.arrow-kt", "arrow-stack").versionRef("arrow")
            library("arrow-core", "io.arrow-kt", "arrow-core").withoutVersion()
            library("arrow-fx-coroutines", "io.arrow-kt", "arrow-fx-coroutines").withoutVersion()

            library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").withoutVersion()
            library("spring-boot-docker-compose", "org.springframework.boot", "spring-boot-docker-compose").withoutVersion()

            // Test libraries
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-graphql-test", "org.springframework.graphql", "spring-graphql-test").withoutVersion()
            library("spring-boot-testcontainers", "org.springframework.boot", "spring-boot-testcontainers").withoutVersion()
            library("reactor-test", "io.projectreactor", "reactor-test").withoutVersion()
            library("kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").withoutVersion()
            library("testcontainers-junit-jupiter", "org.testcontainers", "junit-jupiter").withoutVersion()
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()
        }
    }
}