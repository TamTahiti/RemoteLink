# RemoteLink — Android-to-Android Screen Mirroring & Input Forwarding
## Development Plan

## 1. Project Vision

RemoteLink is an Android-to-Android screen mirroring and input forwarding application.

One Android device acts as the HOST and shares its screen, while another Android device acts as the CLIENT and displays the mirrored screen while forwarding user input back to the HOST.

The application supports multiple transport methods (WebRTC, Direct TCP, and Scrcpy) and is designed around a transport-agnostic architecture.

---

## 2. Core Design Principles

- Transport-agnostic architecture
- Hardware accelerated video pipeline
- Low-latency first
- USB and WiFi support
- Secure local-first communication
- Future support for audio streaming
- Comprehensive performance monitoring

---

## 3. Transport Architecture

```text
Capture → Encode → Transport → Decode → Display
Input → Transport → Injection
```

### ITransport

```kotlin
interface ITransport {
    suspend fun connect()
    suspend fun disconnect()
    suspend fun sendPacket(packet: Packet)
}
```

### Supported Transports

1. WebRTC Transport (Primary)
2. Direct TCP Transport
3. Scrcpy Transport

---

## 4. Packet Protocol

All transports use the same packet format.

```kotlin
sealed class Packet {
    data class Video(...)
    data class Audio(...)
    data class Input(...)
    data class Ping(...)
    data class Control(...)
}
```

---

## 5. Technology Stack

### Core

- Kotlin
- Coroutines
- Hilt
- Kotlin Serialization

### Video

- MediaProjection
- MediaCodec Encoder
- MediaCodec Decoder
- SurfaceView

### Networking

- WebRTC
- TLS TCP

### Security

- Android Keystore
- Certificate Pinning
- TLS 1.3

---

## 6. Video Pipeline

HOST

MediaProjection
→ VirtualDisplay
→ MediaCodec Encoder
→ Transport

CLIENT

Transport
→ MediaCodec Decoder
→ SurfaceView

### Supported Codecs

- H.264 (Default)
- H.265
- VP9
- AV1 (if supported)

### Codec Capability Manager

At startup:

- Detect encoder support
- Detect decoder support
- Detect maximum resolution
- Detect maximum FPS
- Hide unsupported options

---

## 7. Input Injection

### Mode 1: Accessibility Service

Primary solution.

Supports:

- Touch
- Swipe
- Multi-touch gestures

### Mode 2: ADB Enhanced Mode

Supports:

- Full touch injection
- Hardware keys
- Gamepad input

### Mode 3: Root Mode

Advanced users only.

---

## 8. Security

### Pairing

- QR code pairing
- Manual IP entry
- mDNS discovery

### Storage

All keys generated inside Android Keystore.

### Additional Protections

- Certificate pinning
- Session timeout
- Device fingerprint verification
- Rate limiting

---

## 9. Monitoring Systems

### Latency Monitor

Tracks:

- Capture latency
- Encode latency
- Network latency
- Decode latency
- Total latency

### Transport Metrics

```kotlin
data class TransportMetrics(
    val latencyMs: Int,
    val packetLoss: Float,
    val bitrateMbps: Float
)
```

### Thermal Monitor

Uses:

PowerManager.getCurrentThermalStatus()

Automatic adaptation:

- 1080p → 720p
- 60 FPS → 30 FPS
- Reduce bitrate

### Network Quality Monitor

Tracks:

- Packet loss
- Jitter
- Throughput
- Connection stability

---

## 10. Developer Benchmark Screen

Displays:

- FPS
- Total latency
- Packet loss
- Bitrate
- Encoder latency
- Decoder latency
- CPU usage
- GPU usage
- Thermal status
- Battery drain

---

## 11. Audio Architecture

Reserved for future implementation.

```kotlin
interface AudioStream
```

Audio is not part of v1 but architecture must support it.

---

## 12. Project Structure

```text
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
```

---

## 13. Development Roadmap

### Phase POC

Goal:

Validate:

MediaProjection
→ Encode
→ Decode
→ Display

Success:

- Stable 60 FPS
- Less than 100ms latency
- One hour stability test

### Phase 1

- Screen capture
- H.264 streaming
- WebRTC transport
- QR pairing

### Phase 2

- Accessibility input forwarding
- Keyboard forwarding
- Latency metrics

### Phase 3

- USB tethering
- Direct TCP transport

### Phase 4

- Scrcpy transport
- ADB enhanced mode

### Phase 5

- Audio streaming
- Adaptive bitrate
- Thermal adaptation
- Transport auto-selection

---

## 14. Key Risks

### Input Injection

Mitigation:

- Accessibility Service
- ADB Enhanced Mode
- Root Mode

### Thermal Throttling

Mitigation:

- Thermal monitoring
- Automatic quality reduction

### Network Reliability

Mitigation:

- WebRTC fallback
- Direct TCP fallback
- Manual IP entry

---

## 15. Recommended MVP

Version 1.0

- Android-to-Android screen mirroring
- Input forwarding
- WebRTC transport
- Direct TCP transport
- QR pairing
- USB tethering
- Hardware accelerated video
- Latency monitoring
- Thermal management

Deferred:

- Audio streaming
- Root mode
- Wireless ADB pairing
- Experimental USB protocols

