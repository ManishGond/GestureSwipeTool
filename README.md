# 🤖 GestureSwipeTool

<p align="center">
  <a href="https://developer.android.com/jetpack/compose">
    <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-%F0%9F%96%A4-blue"/>
  </a>
  <a href="https://developer.android.com/camera">
    <img alt="CameraX" src="https://img.shields.io/badge/CameraX-Preview%20API-%230099CC"/>
  </a>
  <a href="https://developers.google.com/mediapipe">
    <img alt="MediaPipe" src="https://img.shields.io/badge/MediaPipe-Hand%20Tracking-red"/>
  </a>
  <a href="https://developer.android.com/kotlin">
    <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-Android-%23A97BFF"/>
  </a>
  <a href="https://developer.android.com/jetpack/androidx/releases/hilt">
    <img alt="Hilt" src="https://img.shields.io/badge/Hilt-DI-%232196F3"/>
  </a>
  <a href="https://developer.android.com/topic/architecture">
    <img alt="MVVM" src="https://img.shields.io/badge/MVVM-Architecture-%23FF6F00"/>
  </a>
</p>

An Android app that lets you **control volume and screen brightness** using real-time **hand gestures** detected via the front camera.  
No touch required — just swipe in the air ✋💨

![Swipe Down](https://github.com/user-attachments/assets/4bb4757f-b1b4-497a-9fea-4b2ed43961fc)
![Swipe Up](https://github.com/user-attachments/assets/b3ae917c-521e-44d1-a944-3b674f3d5148)
![Swipe Right](https://github.com/user-attachments/assets/5e318e02-7cee-4749-b976-f17f5fe6673d)
![Swipe Left](https://github.com/user-attachments/assets/d3a0ebcf-1c40-4c91-93fc-be10c970e933)

---

## 🧠 Features

- 👋 **Hand Gesture Recognition** (MediaPipe)
- 🔊 **Swipe Up/Down** to control **volume**
- 💡 **Swipe Left/Right** to adjust **screen brightness**
- ✊ **Fist Gesture** to reset gesture detection
- ⚡ Low-latency frame processing with CameraX
- ⚙️ Built entirely with **Jetpack Compose**, **Hilt**, and **Kotlin**

---

## 🎥 Demo

| Gesture        | Action                  |
|----------------|--------------------------|
| 👆 Swipe Up     | Increase Volume          |
| 👇 Swipe Down   | Decrease Volume          |
| 👉 Swipe Right  | Increase Brightness      |
| 👈 Swipe Left   | Decrease Brightness      |
| ✊ Fist         | Reset Gesture Detection  |

---

## 🔧 Tech Stack

| Tech                         | Purpose                            |
|------------------------------|-------------------------------------|
| **Jetpack Compose**          | Modern declarative UI framework     |
| **CameraX**                  | Real-time front-camera feed         |
| **MediaPipe Hand Landmarker**| Gesture tracking and analysis       |
| **Hilt (DI)**                | Dependency Injection                |
| **Android ViewModel + State**| Handling logic & state management   |
| **Kotlin**                   | Primary language for Android logic  |

---

## 🚀 Getting Started

### 1. Clone the project

```bash
git clone https://github.com/your-username/GestureSwipeTool.git
cd GestureSwipeTool
