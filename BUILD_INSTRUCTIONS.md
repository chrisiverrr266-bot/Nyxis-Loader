# Building Nyxis Loader in Android IDE

## Quick Build (Recommended)

### Using Android IDE GUI:

1. **Open Project**
   - Launch Android IDE
   - Open the `Nyxis-Loader` folder

2. **Sync Gradle**
   - Android IDE will prompt to sync
   - Click "Sync Now" or wait for auto-sync
   - This may take 5-10 minutes on first sync

3. **Build APK**
   - Click **Build** menu
   - Select **Build Bundle(s) / APK(s)**
   - Click **Build APK(s)**
   - Wait for build to complete

4. **Find Your APK**
   - Location: `app/build/outputs/apk/debug/app-debug.apk`
   - Install on your device

## Alternative: Manual Gradle Setup

If Gradle wrapper is missing:

### Download Gradle Wrapper JAR manually:

```bash
# Create directory
mkdir -p gradle/wrapper

# Download gradle-wrapper.jar
cd gradle/wrapper
wget https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar

# Or using curl
curl -o gradle-wrapper.jar https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar
```

### Or let Android IDE handle it:

Android IDE automatically downloads Gradle when you:
1. Open the project
2. Sync Gradle files
3. Build the project

## Troubleshooting

### "Permission denied" on gradlew:
```bash
chmod +x gradlew
```

### "GradleWrapperMain not found":
- Don't use command line
- Use Android IDE's Build menu instead
- IDE handles Gradle internally

### Build fails:
1. Clear cache: **Build → Clean Project**
2. Invalidate caches: **File → Invalidate Caches**
3. Restart Android IDE
4. Sync again

## Requirements

- Android IDE installed
- Internet connection (for first build)
- At least 2GB free storage
- Android SDK installed (comes with IDE)

## Build Time

- First build: 10-20 minutes (downloads dependencies)
- Subsequent builds: 2-5 minutes

## Output

Successful build creates:
- `app-debug.apk` (for testing)
- Located in: `app/build/outputs/apk/debug/`

---

**Tip:** Always use Android IDE's GUI build system rather than command line for best compatibility!
