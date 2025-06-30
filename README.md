# 🤖 GestureSwipeTool

An Android app that lets you **control volume and screen brightness** using real-time **hand gestures** detected via the front camera.  
No touch required — just swipe in the air ✋💨

---

## 🧠 Features

- 👋 **Hand Gesture Recognition** (using MediaPipe)
- 🔊 **Swipe Up/Down** to control **volume**
- 💡 **Swipe Left/Right** to adjust **screen brightness**
- ✊ **Fist Gesture** to reset detection
- 📷 Front Camera powered by **CameraX**
- ⚙️ Built with **Jetpack Compose**, **Hilt**, and **Kotlin**

---

## 🎥 Demo

| Gesture | Action |
|--------|--------|
| 👆 Swipe Up | Increase Volume |
| 👇 Swipe Down | Decrease Volume |
| 👉 Swipe Right | Increase Brightness |
| 👈 Swipe Left | Decrease Brightness |
| ✊ Fist | Reset Gesture Detection |

---

## 🔧 Tech Stack

| Tech | Purpose |
|------|---------|
| Jetpack Compose | Modern declarative UI |
| CameraX | Live camera feed for detection |
| MediaPipe (Hand Landmarker) | Real-time hand gesture tracking |
| Hilt | Dependency Injection |
| ViewModel + State | Gesture and system control logic |

---

## 🚀 Getting Started

### 1. Clone the project

```bash
git clone https://github.com/your-username/GestureSwipeTool.git
cd GestureSwipeTool
