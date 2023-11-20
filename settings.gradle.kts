rootProject.name = "ok-real-estate-advertisements"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

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