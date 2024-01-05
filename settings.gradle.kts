rootProject.name = "ok-real-estate-advertisements"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-spring-boot-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
        id("org.openapi.generator") version openapiVersion apply false

    }
}

//include("m1l1-hello")
//include("m1l3-oop")
//include("m1l4-dsl")

include("ok-real-estate-advertisements-acceptance")

include("ok-real-estate-advertisements-api-v1-jackson")

include("ok-real-estate-advertisements-common")
include("ok-real-estate-advertisements-mappers-v1")

include("ok-real-estate-advertisements-stubs")

include("ok-real-estate-advertisements-biz")

include("ok-real-estate-advertisements-app-spring")

include("ok-real-estate-advertisements-app-kafka")