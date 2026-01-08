# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

-keep class com.nyxis.loader.** { *; }
-keepclassmembers class com.nyxis.loader.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}