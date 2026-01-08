# Nyxis Loader 🚀

<div align="center">

![Version](https://img.shields.io/badge/version-1.0-green)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![License](https://img.shields.io/badge/license-MIT-orange)

**Advanced Android Library Injector for Call of Duty Mobile**

*Download and inject .so libraries directly from GitHub with root and virtual support*

</div>

## ✨ Features

- 📥 **Direct Download**: Automatically downloads libraries from GitHub
- 🔐 **Dual Mode Support**: Works with both rooted and virtual environments
- 🎯 **Smart Injection**: Intelligent injection methods based on device capabilities
- 🎮 **CODM Optimized**: Specifically designed for Call of Duty Mobile
- 🔄 **Real-time Status**: Live injection status updates
- 🎨 **Modern UI**: Clean, dark-themed interface

## 📋 Requirements

- Android 5.0+ (API 21+)
- Internet connection for library downloads
- Root access (optional but recommended)
- Call of Duty Mobile installed

## 🛠️ Setup in Android IDE

### 1. Clone the Repository

```bash
git clone https://github.com/chrisiverrr266-bot/Nyxis-Loader.git
```

### 2. Open in Android IDE

1. Launch **Android IDE**
2. Click **File → Open**
3. Navigate to the cloned `Nyxis-Loader` directory
4. Click **OK** to open the project

### 3. Sync Gradle

The IDE will automatically detect the Gradle configuration:
- Wait for Gradle sync to complete
- All dependencies will be downloaded automatically

### 4. Configure Library URL (Optional)

The loader is pre-configured to download from your library repository:

```java
// In MainActivity.java
private static final String LIB_URL = 
    "https://raw.githubusercontent.com/chrisiverrr266-bot/My-libs-/main/libNyxisCheat.so";
```

To change the library source, edit this URL.

## 🔨 Building the APK

### Method 1: Using Android IDE

1. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for build to complete
3. APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

### Method 2: Using Gradle Command

```bash
./gradlew assembleDebug
```

For release build:

```bash
./gradlew assembleRelease
```

## 📱 Installation & Usage

### Installation

1. Transfer the built APK to your Android device
2. Enable **Unknown Sources** in device settings
3. Install the APK
4. Grant all requested permissions

### Usage

1. **Open Nyxis Loader**
2. **Launch CODM** (if not already running)
3. **Click "Inject Library"**
   - The app will download `libNyxisCheat.so` from GitHub
   - Injection will proceed automatically
   - Status updates appear in real-time
4. **Check Status**
   - "Injection successful!" = Library loaded
   - Error messages indicate issues

## 🔧 How It Works

### Download Process

```
1. User clicks "Inject Library"
2. OkHttp downloads libNyxisCheat.so from GitHub
3. Library saved to app's private directory
4. Injection process begins
```

### Injection Methods

#### Root Mode (Recommended)
```
1. Detects SU binary
2. Obtains root privileges
3. Copies library to /data/local/tmp/
4. Injects into target process memory
5. Loads library using ptrace
```

#### Virtual Mode (Non-Root)
```
1. Uses LD_PRELOAD technique
2. Modifies process environment
3. Preloads library before target starts
4. Library hooks game functions
```

## 📂 Project Structure

```
Nyxis-Loader/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/nyxis/loader/
│   │   │   │   ├── MainActivity.java        # Main UI and logic
│   │   │   │   └── NativeInjector.java      # Injection implementation
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml    # UI layout
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   └── AndroidManifest.xml          # App manifest
│   │   └── build.gradle                     # App dependencies
│   └── proguard-rules.pro                   # ProGuard configuration
├── build.gradle                             # Root build config
├── settings.gradle                          # Project settings
└── README.md                                # This file
```

## 🎯 Customization

### Change Target Package

Edit `MainActivity.java`:

```java
private static final String GAME_PACKAGE = "com.your.game.package";
```

### Add More Libraries

Modify the library URL or add multiple download options:

```java
private static final String[] LIB_URLS = {
    "https://raw.githubusercontent.com/user/repo/main/lib1.so",
    "https://raw.githubusercontent.com/user/repo/main/lib2.so"
};
```

### Custom UI Theme

Edit `app/src/main/res/values/themes.xml` and `colors.xml`.

## 🔐 Permissions Explained

| Permission | Purpose |
|------------|--------|
| `INTERNET` | Download libraries from GitHub |
| `WRITE_EXTERNAL_STORAGE` | Save downloaded libraries (Android <13) |
| `READ_EXTERNAL_STORAGE` | Access saved libraries (Android <13) |

## ⚠️ Disclaimer

**Educational Purpose Only**

This tool is created for educational and research purposes. Users are responsible for complying with:
- Game Terms of Service
- Local laws and regulations
- Anti-cheat policies

The developers assume no liability for misuse.

## 🐛 Troubleshooting

### Injection Failed
- **Ensure root access**: Run a root checker app
- **Verify CODM is running**: Launch the game first
- **Check library integrity**: Verify download completed
- **Permissions**: Grant all requested permissions

### Download Failed
- **Internet connection**: Check network connectivity
- **GitHub access**: Verify repository is public
- **Firewall**: Disable VPN/firewall temporarily

### App Crashes
- **Clear app data**: Settings → Apps → Nyxis Loader → Clear Data
- **Reinstall**: Uninstall and reinstall the app
- **Check logs**: Use `adb logcat` for detailed errors

## 📚 Dependencies

- **OkHttp 4.12.0**: HTTP client for downloads
- **AndroidX AppCompat**: Compatibility library
- **Material Components**: UI components
- **ConstraintLayout**: Layout management

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit changes: `git commit -m 'Add AmazingFeature'`
4. Push to branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 📧 Support

For issues and questions:
- Open an issue on GitHub
- Check existing issues for solutions

---

<div align="center">

**Made with ❤️ for the Android modding community**

⭐ Star this repo if you find it useful!

</div>