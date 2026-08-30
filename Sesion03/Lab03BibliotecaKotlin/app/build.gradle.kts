plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.garcia.lab03bibliotecakotlin"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.garcia.lab03bibliotecakotlin"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}


// ==========================================
// EJECUTAR BIBLIOTECA POR CONSOLA
// ==========================================

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Ejecuta BibliotecaConsola por consola"

    dependsOn("compileDebugKotlin")

    classpath = files(
        layout.buildDirectory.dir(
            "intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes"
        ),
        configurations.getByName("debugRuntimeClasspath")
    )

    mainClass.set("com.garcia.lab03bibliotecakotlin.BibliotecaConsolaKt")

    standardInput = System.`in`
}