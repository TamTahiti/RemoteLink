<!-- ===================================================== --><!--                    PROJECT HEADER                     --><!-- ===================================================== --><p align="center">

</p><h1 align="center">RemoteLink</h1><p align="center">
  Android-to-Android Screen Mirroring & Input Forwarding
</p><p align="center">  <img src="https://img.shields.io/badge/Platform-Android-green">  <img src="https://img.shields.io/badge/Language-Kotlin-purple">  <img src="https://img.shields.io/badge/MinSDK-26-blue">  <img src="https://img.shields.io/badge/License-MIT-orange">  <img src="https://img.shields.io/badge/Status-Active%20Development-success"></p><p align="center">
  Mirror one Android device to another with low latency over WiFi or USB while forwarding touch and keyboard input.
</p>---

Overview

RemoteLink is an Android application that allows one Android device to act as a HOST and another Android device to act as a CLIENT.

The HOST shares its screen in real time while the CLIENT displays the video stream and forwards user input back to the HOST.

The project focuses on:

- Low latency
- Hardware acceleration
- USB and WiFi connectivity
- Secure local communication
- Transport-independent architecture

---

Screenshots

«Screenshots will be added as development progresses.»

<p align="center">
  <img src="docs/images/screenshot-host.png" width="45%">
  <img src="docs/images/screenshot-client.png" width="45%">
</p>---

Features

Screen Mirroring

- Hardware accelerated screen capture
- Hardware video encoding and decoding
- Adjustable resolution and bitrate
- Low-latency streaming

Input Forwarding

- Touch forwarding
- Swipe gestures
- Multi-touch support
- Keyboard forwarding

Connectivity

- WebRTC transport
- Direct TCP transport
- USB tethering support
- QR code pairing
- Automatic device discovery
- Manual IP connection

Security

- Android Keystore
- TLS encryption
- Certificate pinning
- Device verification

Diagnostics

- FPS monitoring
- Latency monitoring
- Packet loss monitoring
- Thermal monitoring
- Network quality monitoring

---

Architecture

Video Pipeline

HOST

MediaProjection
↓
MediaCodec Encoder
↓
Transport
↓

CLIENT

Transport
↓
MediaCodec Decoder
↓
SurfaceView

Input Pipeline

CLIENT

Touch / Keyboard
↓
Transport
↓

HOST

Input Injection

Transport Layer

Capture
↓
Encode
↓
Transport
↓
Decode
↓
Display

Input
↓
Transport
↓
Injection

The transport layer can be replaced without changing the rest of the application.

---

Supported Transports

Transport| Purpose
WebRTC| Primary WiFi transport
Direct TCP| USB tethering and fallback transport
Scrcpy| Advanced transport for enhanced input support

---

Technology Stack

Core

- Kotlin
- Coroutines
- Hilt
- Kotlin Serialization

Video

- MediaProjection
- MediaCodec

Networking

- WebRTC
- TLS TCP

Security

- Android Keystore
- TLS 1.3
- Certificate Pinning

---

Project Structure

RemoteLink/
├── app/
├── core/
├── capture/
├── streaming/
├── transport/
├── input/
├── pairing/
├── ui/
└── common/

---

Roadmap

Phase 1

- Screen capture
- Hardware encoding
- WebRTC transport
- Device pairing

Phase 2

- Touch forwarding
- Keyboard forwarding
- Accessibility injection

Phase 3

- USB tethering
- Direct TCP transport

Phase 4

- Scrcpy transport
- Enhanced input support

Phase 5

- Audio streaming
- Adaptive bitrate
- Transport auto-selection

---

Performance Goals

Metric| Target
Latency| < 100ms
Frame Rate| 60 FPS
Video Codec| H.264
Resolution| Up to 1080p
Connection Types| WiFi + USB

---

License

MIT License

---

<p align="center">
  Built with Kotlin and Android MediaCodec
</p>
