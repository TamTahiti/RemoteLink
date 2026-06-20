// Declare all plugins here once — submodules apply without version
plugins {
    id("com.android.application")                  version "8.5.2"    apply false
    id("com.android.library")                       version "8.5.2"    apply false
    id("org.jetbrains.kotlin.android")              version "2.0.21" apply false
    id("org.jetbrains.kotlin.kapt")                 version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21" apply false
    id("com.google.dagger.hilt.android")            version "2.52"   apply false
}
