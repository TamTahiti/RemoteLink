package com.remotelink.capture

import android.media.projection.MediaProjection
import javax.inject.Inject
import javax.inject.Singleton

/** Owns the MediaProjection lifecycle on the HOST side. */
@Singleton
class ScreenCaptureManager @Inject constructor() {

    private var projection: MediaProjection? = null

    fun start(p: MediaProjection) { projection = p }

    fun stop() {
        projection?.stop()
        projection = null
    }
}
