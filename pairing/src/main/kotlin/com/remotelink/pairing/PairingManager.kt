package com.remotelink.pairing

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles device pairing.
 * - QR code  → ZXing (generate on HOST, scan on CLIENT)
 * - mDNS     → Android NsdManager (no extra dependency)
 * - Manual   → user types HOST IP address
 */
@Singleton
class PairingManager @Inject constructor() {
    // TODO Phase 1: implement QR generation + mDNS broadcast
}
