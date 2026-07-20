# 禁用强制亮度 / NoBrightness

[English](#english) | [中文](#中文)

---


## English

A module designed to prevent specific applications from overriding system screen brightness.

### Features
Restricts background or foreground apps within the scope from automatically forcing the phone screen brightness to maximum.
* **Why it matters**: On devices with exceptionally high peak brightness (like the Google Pixel series), certain apps automatically maximize screen brightness when displaying payment or transit QR codes. This intense glare often causes scanning devices to fail due to overexposure. By disabling this forced brightness, the scannability of QR codes is significantly improved.

### How to Use
1. **Install the Module**: Download and install the latest APK.
2. **Configure the Scope**: Enable this module in your framework (e.g., LSPosed) and select the target apps you wish to restrict (such as WeChat, Alipay, or transit apps).
3. **Restart & Enjoy**: Force stop the target apps or restart your device for the changes to take effect.

### Acknowledgements
* **Gemini**

### License
This project is licensed under the [MIT](LICENSE) License.


---
## 中文

一个用于阻止特定应用修改屏幕亮度的模块。

### 模块作用
对于作用域内的 APP，禁止其自动调整手机屏幕亮度。
* **主要解决痛点**：对于 Pixel 等激发亮度过强的设备，部分应用（如支付、出行类）在展示付款码或乘车码时会强制将亮度调至最高，反而导致扫码枪因反光过度而无法识别。限制其强制亮度有助于大幅提升被识别能力。

### 如何食用
1. **安装模块**：下载并安装本模块的 APK。
2. **配置作用域**：在支持的注入框架（如 LSPosed）中激活本模块，并勾选你需要限制的目标应用（如微信、支付宝、各类公交出行 App）。
3. **重启生效**：重启目标应用或重启手机即可生效。

### 致谢
* **Gemini**

### 开源协议
本项目采用 [MIT](LICENSE) 协议开源。
