import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.configure<DetektExtension>("detekt") {
        buildUponDefaultConfig = true
        allRules = false
        config.setFrom(rootProject.files("config/detekt/detekt.yml"))
        baseline = rootProject.file("config/detekt/baseline.xml")
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "21"
        autoCorrect = providers.gradleProperty("detektAutoCorrect")
            .map(String::toBoolean)
            .getOrElse(false)

        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
        }
    }

    dependencies {
        "detektPlugins"(rootProject.libs.detekt.formatting)
    }
}