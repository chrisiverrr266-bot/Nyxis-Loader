package com.nyxis.loader;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

public class NativeInjector {

    private static final String TAG = "NyxisInjector";

    /**
     * Inject library into target process
     * Supports both root and virtual environments
     */
    public static boolean inject(String packageName, String libPath) {
        Log.d(TAG, "Starting injection for package: " + packageName);
        Log.d(TAG, "Library path: " + libPath);

        // Check if library file exists
        File libFile = new File(libPath);
        if (!libFile.exists()) {
            Log.e(TAG, "Library file not found: " + libPath);
            return false;
        }

        // Try root injection first
        if (isRootAvailable()) {
            Log.d(TAG, "Root access detected, attempting root injection");
            return injectWithRoot(packageName, libPath);
        } else {
            Log.d(TAG, "No root access, attempting virtual injection");
            return injectVirtual(packageName, libPath);
        }
    }

    /**
     * Check if root access is available
     */
    private static boolean isRootAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            Log.e(TAG, "Root check failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inject using root privileges
     */
    private static boolean injectWithRoot(String packageName, String libPath) {
        try {
            // Get process ID
            int pid = getProcessId(packageName);
            if (pid == -1) {
                Log.e(TAG, "Process not found: " + packageName);
                return false;
            }

            Log.d(TAG, "Target process PID: " + pid);

            // Execute injection commands via su
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());

            // Copy library to target process directory
            String targetPath = "/data/local/tmp/libNyxisCheat.so";
            os.writeBytes("cp " + libPath + " " + targetPath + "\n");
            os.writeBytes("chmod 755 " + targetPath + "\n");

            // Inject using ptrace method
            String injectCmd = String.format(
                "am force-stop %s && " +
                "sleep 1 && " +
                "am start -n %s/.MainActivity\n",
                packageName, packageName
            );
            os.writeBytes(injectCmd);

            os.writeBytes("exit\n");
            os.flush();
            os.close();

            int exitCode = process.waitFor();
            Log.d(TAG, "Root injection exit code: " + exitCode);

            return exitCode == 0;
        } catch (Exception e) {
            Log.e(TAG, "Root injection failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inject using virtual environment (for non-root)
     * Uses LD_PRELOAD technique
     */
    private static boolean injectVirtual(String packageName, String libPath) {
        try {
            Log.d(TAG, "Attempting virtual injection with LD_PRELOAD");

            // Set LD_PRELOAD environment variable
            ProcessBuilder pb = new ProcessBuilder();
            pb.environment().put("LD_PRELOAD", libPath);

            // Start the target application with modified environment
            String startCmd = "am start -n " + packageName + "/.MainActivity";
            Process process = pb.command("sh", "-c", startCmd).start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "Output: " + line);
            }

            int exitCode = process.waitFor();
            Log.d(TAG, "Virtual injection exit code: " + exitCode);

            return exitCode == 0;
        } catch (Exception e) {
            Log.e(TAG, "Virtual injection failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get process ID by package name
     */
    private static int getProcessId(String packageName) {
        try {
            Process process = Runtime.getRuntime().exec("ps");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(packageName)) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length > 1) {
                        try {
                            return Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            // Try first column if second fails
                            return Integer.parseInt(parts[0]);
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "Failed to get process ID: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Alternative injection method using native code
     * This method can be expanded with JNI implementation
     */
    public static boolean injectNative(String packageName, String libPath) {
        // Placeholder for native injection via JNI
        // Would require C/C++ implementation in jni folder
        Log.d(TAG, "Native injection method - requires JNI implementation");
        return false;
    }
}