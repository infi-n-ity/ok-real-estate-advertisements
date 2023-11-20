plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-real-estate-advertisements-api-v1-jackson"))
    implementation(project(":ok-real-estate-advertisements-common"))

    testImplementation(kotlin("test-junit"))
}