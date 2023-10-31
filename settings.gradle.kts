rootProject.name = "ok-real-estate-advertisements"
include("m1l1-hello")
include("m1l3-oop")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}

