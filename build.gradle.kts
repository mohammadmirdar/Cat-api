// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.realmKotlin) apply false
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    id ("org.jetbrains.kotlin.jvm") version "1.9.24"

}