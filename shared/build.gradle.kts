plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.9.0"

    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0"
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")

}
@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    val mokoResourceVersion = extra["moko.version.resources"] as String
    val ktorVersion = "2.3.3"
    val koin = "3.2.0"
    val voyagerVersion = "1.0.0-rc05"
    val multiplatformSettings = "1.0.0"

    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "16.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            export("dev.icerock.moko:resources:$mokoResourceVersion")
            export("dev.icerock.moko:graphics:0.9.0 ")
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }


    // Multiplatform

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$1.9.0")

                implementation("io.insert-koin:koin-core:${koin}")
                implementation("io.insert-koin:koin-test:${koin}")
                implementation("io.insert-koin:koin-compose:1.0.4")

                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-rc02")

                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatfrom
                api("dev.icerock.moko:mvvm-test:0.16.1") // api mvvm-test
                api("dev.icerock.moko:test-core:0.6.1")
                api("dev.icerock.moko:resources:$mokoResourceVersion")
                api("dev.icerock.moko:resources-compose:$mokoResourceVersion")

                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion") //Mavigation
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // #1 - Basic settings
                implementation("com.russhwolf:multiplatform-settings-no-arg:$multiplatformSettings")



            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")  // Kotlinx Serialization JSON support

            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("app.cash.sqldelight:android-driver:2.0.0")
                implementation("io.insert-koin:koin-android:${koin}")
                implementation("androidx.compose.material3:material3:1.1.1")



            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("app.cash.sqldelight:native-driver:2.0.0")

            }
        }

    }
}
sqldelight {
    databases {
        create("SportDatabase") {
            packageName.set("com.example.yoursportapp")
        }
    }
}
android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}

multiplatformResources{
    multiplatformResourcesClassName = "SharedRes" // optional, default MR
    disableStaticFrameworkWarning = true


}
