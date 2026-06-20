package com.remotelink.transport

import com.remotelink.common.Packet

/**
 * Transport-agnostic interface.
 * Swap WebRTC, Direct TCP, or Scrcpy without touching any other module.
 */
interface ITransport {
    suspend fun connect()
    suspend fun disconnect()
    suspend fun sendPacket(packet: Packet)
    fun isConnected(): Boolean
}
