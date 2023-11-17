// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.gradle) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlyticsPlugin) apply false
    alias(libs.plugins.performance) apply false
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.libraryAndroid) apply false
}