plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    val kotestVersion: String by project
    val springdocOpenapiUiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project


    implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.4") // Controller, Service, etc..
    // implementation("org.springframework.boot:spring-boot-starter-websocket") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib") // for spring-boot app
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // transport models
    implementation(project(":ok-real-estate-advertisements-common"))

    // v1 api
    implementation(project(":ok-real-estate-advertisements-api-v1-jackson"))
    implementation(project(":ok-real-estate-advertisements-mappers-v1"))

    // biz
    implementation(project(":ok-real-estate-advertisements-biz"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux:3.0.4") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:3.0.1") // mockking beans
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/specs") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
