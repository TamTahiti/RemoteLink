package com.remotelink.input

import com.remotelink.common.Packet
import javax.inject.Inject
import javax.inject.Singleton

/** Receives Input packets and injects gestures/keys into the HOST device. */
@Singleton
class InputForwarder @Inject constructor() {

    fun forward(packet: Packet.Input) {
        // TODO Phase 2: AccessibilityService  (touch / swipe / multi-touch)
        // TODO Phase 4: ADB Enhanced Mode     (hardware keys / gamepad)
    }
}
