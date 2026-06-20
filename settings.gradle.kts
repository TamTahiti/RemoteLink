pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RemoteLink"
include(
    ":app", ":core", ":common",
    ":capture", ":streaming", ":transport",
    ":input", ":pairing", ":ui"
)
