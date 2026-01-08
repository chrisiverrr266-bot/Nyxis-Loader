# Nyxis Loader 🚀

<div align="center">

![Version](https://img.shields.io/badge/version-1.0-green)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![License](https://img.shields.io/badge/license-MIT-orange)

**Advanced Android Library Injector for Call of Duty Mobile**

*Download and inject .so libraries directly from GitHub with root and virtual support*

[📱 Join Discussion](https://t.me/indradiscussion) • [💬 Contact Dev](https://t.me/iinddra)

</div>

---

## ✨ Features

### 🎨 Premium UI/UX
- **Custom Button Designs**: Gradient inject button, outlined support buttons
- **Navigation Drawer**: Slide-out menu with Nyxis branding
- **Modern Dark Theme**: Sleek black and neon green interface
- **Animated Elements**: Ripple effects and smooth transitions
- **Status Cards**: Real-time injection status with styled backgrounds

### 🔧 Core Functionality
- 📥 **Direct Download**: Automatically downloads libraries from GitHub
- 🔐 **Dual Mode Support**: Works with both rooted and virtual environments
- 🎯 **Smart Injection**: Intelligent injection methods based on device capabilities
- 🎮 **CODM Optimized**: Specifically designed for Call of Duty Mobile
- 🔄 **Real-time Status**: Live injection status updates

### 🤝 Community Integration
- 💬 **Support Channel**: Direct link to [Telegram Discussion Group](https://t.me/indradiscussion)
- 📧 **Direct Contact**: One-tap access to developer [@iinddra](https://t.me/iinddra)
- ℹ️ **About Section**: Complete app information in navigation drawer

## 📱 Screenshots

> Premium dark-themed interface with custom gradient buttons and navigation drawer

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
   - Enjoy the premium UI with custom animations
   
2. **Explore Navigation Drawer** (tap menu icon)
   - View app information
   - Access support channels
   - Contact developer directly
   
3. **Launch CODM** (if not already running)

4. **Click "Inject Library"** (custom gradient button)
   - The app will download `libNyxisCheat.so` from GitHub
   - Injection will proceed automatically
   - Status updates appear in real-time in the styled status card
   
5. **Connect with Community**
   - Tap **"Support Me - Join Discussion"** → Opens [t.me/indradiscussion](https://t.me/indradiscussion)
   - Tap **"Contact Me Directly"** → Opens [t.me/iinddra](https://t.me/iinddra)

## 🎨 Design Highlights

### Custom Buttons
- **Inject Button**: Gradient background (green) with white border
- **Support Button**: Outlined style with Telegram icon
- **Contact Button**: Blue-themed outlined design
- All buttons feature ripple effects on tap

### Navigation Drawer
- **Header**: "Made by Nyxis" branding with gradient background
- **Menu Items**: Home, About, Support Channel, Direct Contact
- **Icons**: Custom-designed vector graphics

### Color Scheme
```xml
- Primary: #00FF88 (Nyxis Green)
- Background: #0a0a0a (Deep Black)
- Cards: #1a1a1a to #2a2a2a (Gradient)
- Text: #FFFFFF (White)
- Secondary: #00A8FF (Telegram Blue)
```

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
│   │   │   │   ├── MainActivity.java        # Main UI, drawer, Telegram links
│   │   │   │   └── NativeInjector.java      # Injection implementation
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── button_inject.xml    # Custom inject button
│   │   │   │   │   ├── button_support.xml   # Support button style
│   │   │   │   │   ├── button_contact.xml   # Contact button style
│   │   │   │   │   ├── status_background.xml # Status card design
│   │   │   │   │   ├── card_background.xml   # Card backgrounds
│   │   │   │   │   ├── ic_menu.xml          # Menu icon
│   │   │   │   │   ├── ic_telegram.xml      # Telegram icon
│   │   │   │   │   └── ic_info.xml          # Info icon
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml    # Main UI with drawer
│   │   │   │   │   └── nav_header.xml       # Drawer header
│   │   │   │   ├── menu/
│   │   │   │   │   └── nav_menu.xml         # Navigation menu
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle                     # App dependencies
│   └── proguard-rules.pro
├── build.gradle                             # Root build config
├── settings.gradle
└── README.md
```

## 🎯 Customization

### Change Telegram Links

Edit `MainActivity.java`:

```java
private static final String TELEGRAM_DISCUSSION = "https://t.me/indradiscussion";
private static final String TELEGRAM_CONTACT = "https://t.me/iinddra";
```

### Change Target Package

```java
private static final String GAME_PACKAGE = "com.your.game.package";
```

### Modify Button Colors

Edit `app/src/main/res/drawable/button_*.xml` files:

```xml
<!-- Change gradient colors -->
<gradient
    android:startColor="#YOUR_COLOR_1"
    android:endColor="#YOUR_COLOR_2" />
```

### Update Branding

Edit `nav_header.xml` to change "Made by Nyxis" text.

## 🔐 Permissions Explained

| Permission | Purpose |
|------------|--------|
| `INTERNET` | Download libraries from GitHub, open Telegram links |
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

### Telegram Links Not Opening
- **Install Telegram**: Download from Play Store
- **Default browser**: Links will open in browser if Telegram not installed

### App Crashes
- **Clear app data**: Settings → Apps → Nyxis Loader → Clear Data
- **Reinstall**: Uninstall and reinstall the app
- **Check logs**: Use `adb logcat` for detailed errors

## 📚 Dependencies

- **OkHttp 4.12.0**: HTTP client for downloads
- **AndroidX AppCompat**: Compatibility library
- **Material Components**: UI components and design
- **ConstraintLayout**: Layout management
- **DrawerLayout**: Navigation drawer functionality

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit changes: `git commit -m 'Add AmazingFeature'`
4. Push to branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request

## 💬 Community & Support

### Join Our Community
- **Discussion Group**: [t.me/indradiscussion](https://t.me/indradiscussion)
  - Get help from the community
  - Share experiences
  - Latest updates and news

### Contact Developer
- **Direct Contact**: [t.me/iinddra](https://t.me/iinddra)
  - Report bugs
  - Feature requests
  - Technical support

### GitHub Issues
- Open an issue for bug reports
- Check existing issues for solutions

## 📄 License

This project is licensed under the MIT License.

## 🌟 Credits

**Made with ❤️ by Nyxis**

- Design & Development: Nyxis Team
- UI/UX: Custom Material Design implementation
- Special thanks to the Android modding community

---

<div align="center">

### 🚀 Ready to use Nyxis Loader?

⭐ **Star this repo** if you find it useful!

[📥 Download Latest Release](https://github.com/chrisiverrr266-bot/Nyxis-Loader/releases) • [💬 Join Community](https://t.me/indradiscussion) • [📧 Contact](https://t.me/iinddra)

**Made by Nyxis • 2026**

</div>