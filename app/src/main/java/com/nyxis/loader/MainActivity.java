package com.nyxis.loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String LIB_URL = "https://raw.githubusercontent.com/chrisiverrr266-bot/My-libs-/main/libNyxisCheat.so";
    private static final String GAME_PACKAGE = "com.activision.callofduty.shooter";
    private static final String TELEGRAM_DISCUSSION = "https://t.me/indradiscussion";
    private static final String TELEGRAM_CONTACT = "https://t.me/iinddra";
    
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button btnInject, btnSupport, btnContact;
    private ImageButton btnMenu;
    private TextView tvStatus;
    private OkHttpClient httpClient;
    private ExecutorService executorService;
    private Handler mainHandler;
    private File libFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupDrawer();
        setupClickListeners();
        
        httpClient = new OkHttpClient();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
        libFile = new File(getExternalFilesDir(null), "libNyxisCheat.so");

        checkPermissions();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnInject = findViewById(R.id.btnInject);
        btnSupport = findViewById(R.id.btnSupport);
        btnContact = findViewById(R.id.btnContact);
        btnMenu = findViewById(R.id.btnMenu);
        tvStatus = findViewById(R.id.tvStatus);
    }

    private void setupDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
        
        btnMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupClickListeners() {
        btnInject.setOnClickListener(v -> startInjection());
        
        btnSupport.setOnClickListener(v -> {
            openTelegram(TELEGRAM_DISCUSSION);
            Toast.makeText(this, "Opening Telegram Discussion...", Toast.LENGTH_SHORT).show();
        });
        
        btnContact.setOnClickListener(v -> {
            openTelegram(TELEGRAM_CONTACT);
            Toast.makeText(this, "Opening Direct Contact...", Toast.LENGTH_SHORT).show();
        });
    }

    private void openTelegram(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open Telegram. Please install Telegram app.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.nav_home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            showAboutDialog();
        } else if (id == R.id.nav_support) {
            openTelegram(TELEGRAM_DISCUSSION);
        } else if (id == R.id.nav_contact) {
            openTelegram(TELEGRAM_CONTACT);
        }
        
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAboutDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("About Nyxis Loader");
        builder.setMessage(
            "Nyxis Loader v1.0\n\n" +
            "Advanced library injection system for Call of Duty Mobile.\n\n" +
            "Features:\n" +
            "• Root & Virtual support\n" +
            "• Direct GitHub downloads\n" +
            "• Real-time injection\n\n" +
            "Made by Nyxis\n\n" +
            "Support: t.me/indradiscussion\n" +
            "Contact: t.me/iinddra"
        );
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Support", (dialog, which) -> openTelegram(TELEGRAM_DISCUSSION));
        builder.show();
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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