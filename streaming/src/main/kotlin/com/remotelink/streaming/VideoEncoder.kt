package com.remotelink.streaming

import android.media.MediaCodec
import android.media.MediaFormat
import javax.inject.Inject
import javax.inject.Singleton

/** Hardware H.264 encoder — configure then call start(). Phase 1 implementation target. */
@Singleton
class VideoEncoder @Inject constructor() {

    private var codec: MediaCodec? = null

    fun configure(width: Int, height: Int, bitrateBps: Int, fps: Int) {
        val fmt = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
            setInteger(MediaFormat.KEY_BIT_RATE, bitrateBps)
            setInteger(MediaFormat.KEY_FRAME_RATE, fps)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
        }
        codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
        codec?.configure(fmt, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
    }

    fun release() {
        codec?.stop()
        codec?.release()
        codec = null
    }
}
