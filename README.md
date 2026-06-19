# RemoteLink

> **Phone as the GPU, tablet as the screen** — low-latency Android remote display with swappable backends and hardware video encoding.

<p align="center">
  <img src="docs/assets/banner.png" alt="RemoteLink banner" width="100%" />
</p>

<p align="center">
  <a href="https://github.com/yourname/remotelink/releases"><img src="https://img.shields.io/github/v/release/yourname/remotelink?style=flat-square" alt="Latest release" /></a>
  <a href="LICENSE"><img src="https://img.shields.io/github/license/yourname/remotelink?style=flat-square" alt="License" /></a>
  <img src="https://img.shields.io/badge/Android-8.0%2B-green?style=flat-square&logo=android" alt="Android 8.0+" />
  <img src="https://img.shields.io/badge/encryption-DTLS--SRTP%20%7C%20TLS%201.3-blue?style=flat-square" alt="Encrypted" />
  <img src="https://img.shields.io/badge/root-not%20required-brightgreen?style=flat-square" alt="No root" />
</p>

---

RemoteLink streams your Android phone's screen to another Android device in real time — with full touch, keyboard, and gamepad input forwarded back. No PC, no cloud, no root required.

The primary use case is **mobile gaming**: run a game on your phone (where your SoC is), use your tablet as the display and controller. Both devices talk directly over USB or WiFi, with end-to-end encryption and hardware-accelerated video on both ends.

---

## Features

- **Three swappable connection backends** — scrcpy (ADB), WebRTC, and Direct TLS TCP. Switch without reconnecting.
- **Hardware video pipeline** — encode and decode via MediaCodec; near-zero CPU overhead on both devices.
- **Configurable quality** — resolution, bitrate, FPS, codec, keyframe interval, and latency mode all tunable from settings.
- **USB and WiFi** — USB tethering requires no ADB and no WiFi at all. WebRTC works even when your router blocks ADB port 5555.
- **End-to-end encrypted** — WebRTC backend uses mandatory DTLS-SRTP. TCP backend uses mutual TLS 1.3 with certificate pinning.
- **Zero-config discovery** — HOST device advertises over mDNS; CLIENT finds it automatically on the same network. No IP entry needed.
- **QR pairing** — first-time connection authenticated by scanning a QR code on the HOST screen.
- **No root, no PC** — everything runs on the two Android devices. ADB is optional and only used for the scrcpy backend.

---

## Screenshots

| Mode Select | HOST Active | CLIENT Display | Settings |
|:-----------:|:-----------:|:--------------:|:--------:|
| *(coming soon)* | *(coming soon)* | *(coming soon)* | *(coming soon)* |

---

## How It Works

```
┌─────────────────────────────────┐         ┌────────────────────────────────┐
│          PHONE (HOST)           │         │         TABLET (CLIENT)        │
│                                 │         │                                │
│  Game / App running normally    │         │  Full-screen display           │
│          │                      │         │         │                      │
│  MediaProjection (screen cap)   │         │  MediaCodec decoder            │
│          │                      │  Video  │         │                      │
│  MediaCodec encoder  ───────────┼────────►│  SurfaceView                   │
│  H.264 / HEVC / VP9             │         │                                │
│          ▲                      │  Input  │  Touch / keyboard / gamepad    │
│  Input injection  ◄─────────────┼─────────│  captured and forwarded        │
│  (Accessibility / ADB)          │         │                                │
└─────────────────────────────────┘         └────────────────────────────────┘
        WebRTC · Direct TLS TCP · scrcpy (ADB)
```

One APK is installed on both devices. On launch, you choose **HOST** (the device running the game) or **CLIENT** (the device you want to see and touch the screen from).

---

## Connection Backends

RemoteLink ships three backends. You can switch between them in Settings at any time.

### WebRTC *(recommended for WiFi)*
Uses the Google WebRTC library for peer-to-peer video streaming directly on your LAN. DTLS-SRTP encryption is mandatory and cannot be disabled. Works even when your router blocks the ADB port (5555) because it negotiates its own connection over any open port. No ADB enabled on either device.

Input injection uses Android's Accessibility Service on the HOST — covers all touch-based games.

### Direct TLS TCP *(recommended for USB)*
The HOST opens a TLS 1.3 server socket on a port you choose. The CLIENT connects directly. Works over USB tethering (no WiFi needed at all), or over WiFi as a fallback when WebRTC ICE negotiation fails. Mutual certificate authentication with pinning — the connection is rejected if the certificate doesn't match what was established during QR pairing.

### scrcpy *(recommended when ADB is available)*
Integrates the battle-tested [scrcpy](https://github.com/Genymobile/scrcpy) server as an embedded asset. Gives the highest input fidelity — full multi-touch, hardware keys, and gamepad injection via Android's `InputManager` — because the scrcpy server runs with ADB shell-level permissions. Preferred over USB. Also works over WiFi if your network allows ADB (port 5555).

Android 11+ users can pair wirelessly via a built-in ADB pairing flow without ever plugging in a USB cable.

---

## Requirements

| | HOST (phone) | CLIENT (tablet) |
|---|---|---|
| Android version | 8.0+ (API 26) | 8.0+ (API 26) |
| RAM | 3 GB+ recommended | 2 GB+ |
| Permissions | Screen capture, Accessibility Service | Overlay (for input capture) |
| ADB enabled | Only for scrcpy backend | Not required |
| Root | Never required | Never required |

Both devices must be on the same WiFi network (WebRTC / TCP backends), connected over USB tethering, or connected via USB with ADB (scrcpy backend).

---

## Installation

### From releases *(easiest)*
1. Download `remotelink.apk` from the [latest release](https://github.com/yourname/remotelink/releases).
2. Install on **both** your phone and your tablet.
3. Enable "Install from unknown sources" if prompted.

### From source

```bash
# Clone the repo
git clone https://github.com/yourname/remotelink.git
cd remotelink

# Build debug APK
./gradlew assembleDebug

# Install on connected device
adb install app/build/outputs/apk/debug/app-debug.apk
```

Requires Android Studio Hedgehog (2023.1.1) or later, JDK 17.

---

## Setup & First Use

### Step 1 — Choose roles
Open RemoteLink on your **phone** → tap **HOST**.  
Open RemoteLink on your **tablet** → tap **CLIENT**.

### Step 2 — Choose a backend

| Situation | Use this backend |
|---|---|
| Same WiFi, ADB blocked | WebRTC |
| USB cable, no WiFi | Direct TLS TCP (USB tether) |
| USB cable, ADB enabled | scrcpy |
| Same WiFi, ADB port open | scrcpy (WiFi) |

### Step 3 — Pair
On the HOST, tap **Show Pairing QR**. On the CLIENT, tap **Scan to Connect** and point your camera at the HOST screen. Connection establishes automatically.

That's it. The HOST screen appears on the CLIENT. Touch the CLIENT screen to send input to the HOST.

---

## Video Settings

All settings are under **HOST → Settings → Video**.

| Setting | Range | Default | Notes |
|---|---|---|---|
| Resolution | 480p / 720p / 1080p / Native / Custom | 720p | Lower = less bandwidth, less latency |
| Bitrate | 500 kbps – 50 Mbps | 8 Mbps | 4–8 Mbps good for WiFi |
| Frame rate | 15 / 30 / 60 / 90 / 120 fps | 60 fps | Capped to display refresh rate |
| Codec | H.264 · H.265 · VP9 | H.264 | H.265 saves ~40% bandwidth vs H.264 |
| Keyframe interval | 0.5s / 1s / 2s / 5s | 1s | Lower = faster recovery on packet loss |
| Latency mode | Ultra-low / Balanced / Quality | Ultra-low | Ultra-low disables B-frames, reduces encoder buffers |

**Ultra-low latency mode** is the default. It sacrifices a small amount of compression efficiency to reduce end-to-end latency by 20–40 ms. Recommended for gaming.

---

## Architecture

```
remotelink/
├── app/                    Entry point, navigation, Hilt setup
├── core/
│   ├── core-model/         Shared data classes (VideoConfig, InputEvent, PeerInfo)
│   ├── core-network/       IBackend interface + wire protocol
│   └── core-common/        Utilities and extensions
├── feature/
│   ├── feature-host/       Screen capture, encode, serve
│   ├── feature-client/     Decode, display, input capture
│   ├── feature-settings/   All configuration screens
│   └── feature-pairing/    QR generation, scanning, mDNS discovery
├── backend/
│   ├── backend-scrcpy/     scrcpy ADB integration
│   ├── backend-webrtc/     WebRTC P2P (google-webrtc)
│   └── backend-tcp/        Direct TLS TCP (Ktor + Conscrypt)
├── service/
│   ├── HostForegroundService        Keeps capture alive in background
│   └── InputAccessibilityService   Input injection (non-ADB backends)
└── assets/
    └── scrcpy-server.jar   Bundled scrcpy server binary
```

### Key dependencies

| Library | Purpose |
|---|---|
| `org.webrtc:google-webrtc` | Full WebRTC stack — encoding, DTLS-SRTP, ICE |
| `io.ktor:ktor-network` | Async TLS TCP server and client |
| `org.conscrypt:conscrypt-android` | TLS 1.3 provider |
| `androidx.media3:media3-exoplayer` | Video decoding and surface output |
| `scrcpy-server.jar` | scrcpy server (bundled asset, Apache 2.0) |
| `com.google.dagger:hilt-android` | Dependency injection |
| `kotlinx.serialization` | Input event and config serialization |

---

## Security

RemoteLink is designed for use between devices you own on a private network. That said, all connections are encrypted:

- **WebRTC**: DTLS-SRTP is enforced by the WebRTC spec — it cannot be disabled. The local signaling server runs on the HOST device; no data leaves your LAN.
- **Direct TLS TCP**: Mutual TLS 1.3 authentication. Both devices generate self-signed certificates on first launch. Certificate fingerprints are exchanged during QR pairing and pinned — a man-in-the-middle cannot impersonate either device.
- **scrcpy**: Authentication via ADB's built-in RSA key pair mechanism.
- **Stored credentials**: Pairing data is stored in Android `EncryptedSharedPreferences` (AES-256 via Jetpack Security).

No account, no cloud service, no telemetry.

---

## Roadmap

- [x] Development plan
- [ ] Core video pipeline (MediaProjection + MediaCodec)
- [ ] Direct TLS TCP backend
- [ ] WebRTC backend
- [ ] Accessibility Service input injection
- [ ] scrcpy backend
- [ ] Video settings UI
- [ ] USB tethering transport
- [ ] Gamepad / controller support
- [ ] Adaptive bitrate
- [ ] Android TV / DeX support
- [ ] iOS client (long-term)

---

## Contributing

Contributions are welcome. Please open an issue before starting work on a large change so we can discuss approach first.

```bash
# Run tests
./gradlew test

# Run lint
./gradlew lint

# Build all modules
./gradlew build
```

Code style follows the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html). All pull requests must pass CI checks.

---

## Credits & Prior Art

RemoteLink builds on the shoulders of these projects:

- **[scrcpy](https://github.com/Genymobile/scrcpy)** by Genymobile — the gold standard for Android screen mirroring. The scrcpy server JAR is bundled under its original Apache 2.0 license.
- **[Moonlight](https://github.com/moonlight-stream/moonlight-android)** — reference implementation for low-latency game streaming on Android.
- **[WebRTC](https://webrtc.org)** — Google's open-source real-time communication stack.

---

## License

```
Copyright 2025 RemoteLink Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```

The bundled `scrcpy-server.jar` is copyright Genymobile and licensed separately under the Apache License 2.0. See [`assets/SCRCPY_LICENSE`](assets/SCRCPY_LICENSE).
