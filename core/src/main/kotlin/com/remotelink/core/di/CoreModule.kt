package com.remotelink.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Add app-wide @Provides / @Binds here as the project grows. */
@Module
@InstallIn(SingletonComponent::class)
object CoreModule
