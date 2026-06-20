package com.remotelink.common

import kotlinx.serialization.Serializable

/** Every message exchanged between HOST and CLIENT goes through this sealed class. */
@Serializable
sealed class Packet {

    @Serializable
    data class Video(val timestampMs: Long, val keyFrame: Boolean = false) : Packet()

    @Serializable
    data class Audio(val timestampMs: Long) : Packet()

    @Serializable
    data class Input(
        val type: String,       // "touch_down" | "touch_move" | "touch_up" | "key"
        val x: Float    = 0f,
        val y: Float    = 0f,
        val pointerId: Int = 0,
        val keyCode:  Int = 0
    ) : Packet()

    @Serializable
    data class Ping(val timestampMs: Long) : Packet()

    @Serializable
    data class Control(val command: String) : Packet()
}
