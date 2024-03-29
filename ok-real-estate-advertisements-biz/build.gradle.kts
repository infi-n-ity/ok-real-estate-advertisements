plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    macosArm64 {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":ok-real-estate-advertisements-common"))
                implementation(project(":ok-real-estate-advertisements-stubs"))
                implementation(project(":ok-real-estate-advertisements-lib-cor"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":ok-real-estate-advertisements-repo-stubs"))
                implementation(project(":ok-real-estate-advertisements-repo-tests"))
                implementation(project(":ok-real-estate-advertisements-repo-in-memory"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

                implementation(project(":ok-real-estate-advertisements-repo-tests"))
                implementation(project(":ok-real-estate-advertisements-stubs"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
