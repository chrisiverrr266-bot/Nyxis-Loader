package com.nyxis.loader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String LIB_URL = "https://raw.githubusercontent.com/chrisiverrr266-bot/My-libs-/main/libNyxisCheat.so";
    private static final String GAME_PACKAGE = "com.activision.callofduty.shooter";
    
    private Button btnInject;
    private TextView tvStatus;
    private OkHttpClient httpClient;
    private ExecutorService executorService;
    private Handler mainHandler;
    private File libFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInject = findViewById(R.id.btnInject);
        tvStatus = findViewById(R.id.tvStatus);

        httpClient = new OkHttpClient();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        libFile = new File(getExternalFilesDir(null), "libNyxisCheat.so");

        checkPermissions();

        btnInject.setOnClickListener(v -> startInjection());
    }

    private void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void startInjection() {
        btnInject.setEnabled(false);
        updateStatus(getString(R.string.downloading));

        Request request = new Request.Builder()
                .url(LIB_URL)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mainHandler.post(() -> {
                    updateStatus(getString(R.string.error, e.getMessage()));
                    btnInject.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() -> {
                        updateStatus(getString(R.string.error, "Download failed"));
                        btnInject.setEnabled(true);
                    });
                    return;
                }

                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream(libFile)) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    mainHandler.post(() -> {
                        updateStatus(getString(R.string.injecting));
                        injectLibrary();
                    });

                } catch (IOException e) {
                    mainHandler.post(() -> {
                        updateStatus(getString(R.string.error, e.getMessage()));
                        btnInject.setEnabled(true);
                    });
                }
            }
        });
    }

    private void injectLibrary() {
        executorService.execute(() -> {
            try {
                boolean injected = NativeInjector.inject(GAME_PACKAGE, libFile.getAbsolutePath());
                
                mainHandler.post(() -> {
                    if (injected) {
                        updateStatus(getString(R.string.success));
                        Toast.makeText(MainActivity.this, "Library injected successfully!", Toast.LENGTH_LONG).show();
                    } else {
                        updateStatus(getString(R.string.error, "Injection failed"));
                        Toast.makeText(MainActivity.this, "Injection failed. Make sure you have root access.", Toast.LENGTH_LONG).show();
                    }
                    btnInject.setEnabled(true);
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    updateStatus(getString(R.string.error, e.getMessage()));
                    btnInject.setEnabled(true);
                });
            }
        });
    }

    private void updateStatus(String status) {
        tvStatus.setText(status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions required for operation", Toast.LENGTH_SHORT).show();
            }
        }
    }
}