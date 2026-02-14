# 🎨 Modern Picker Pro — Java Desktop Color Tool

<p align="center">
  <img src="src/Screen_Shot/screen.png" alt="Modern Picker Preview" width="400">
</p>

![Java Version](https://img.shields.io/badge/Java-17+-orange?logo=java)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey)
![License](https://img.shields.io/badge/License-MIT-green)

A lightweight and modern desktop utility for capturing colors directly from your screen. Built with **Java Swing**, this tool focuses on aesthetics and functionality, providing developers and designers with instant access to essential color codes.

---

## 📺 About The Project

**Modern Picker Pro** is more than just an eyedropper. It is a compact desktop application that allows you to capture any pixel on your monitor and instantly convert it into popular code formats. The program also identifies the closest color name using a built-in database.

### Key Features:
* **Screen Capture:** Pick colors from any window or desktop element.
* **Smart Naming:** Automatically finds the human-readable name (e.g., "Royal Blue").
* **Multi-format Support:** Generates codes for HEX, HEXA, RGB, RGBA, and HSL.
* **Modern UI:** Dark theme, rounded corners, and dynamic component rendering.

---

## ✅ Feature Checklist

### Supported Formats
- [x] **HEX** — Classic hexadecimal code (#RRGGBB).
- [x] **HEXA** — Hexadecimal with Alpha channel (#RRGGBBAA).
- [x] **RGB** — Standard decimal channel values.
- [x] **RGBA** — RGB with Alpha transparency (0.0 - 1.0).
- [x] **HSL** — Hue, Saturation, and Lightness.

### Technical Implementation
- [x] **Robot API** — System-level screen capturing.
- [x] **Graphics2D Antialiasing** — High-quality rendering for UI elements.
- [x] **Color Matcher** — Euclidean distance algorithm for naming colors.
- [x] **Responsive Layout** — Adaptive text area and scrollable data.

---

## 🛠️ Tech Stack

* **Language:** Java
* **Libraries:** Swing (GUI), AWT (Graphics, Robot).
* **Design:** Flat Dark Minimalist.

---

## 🚀 Getting Started

### Prerequisites
* **JDK 17** or higher installed on your system.

### Installation & Run
1. Clone the repository:
   ```bash
   git clone [https://github.com/your-username/ColorPickerApp.git](https://github.com/your-username/ColorPickerApp.git)
