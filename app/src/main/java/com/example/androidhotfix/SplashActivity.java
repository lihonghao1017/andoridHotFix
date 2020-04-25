package com.example.androidhotfix;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.hotfix.tool.HotFix2;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        requestPemmision();
    }


    private void requestPemmision() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 0x301);//fragment基类中的函数
            } else {
                startHotFix();
            }
        }
    }

    private void startHotFix() {
        String fixFilePath = "/sdcard/hotfix/fix.dex";
        HotFix2.fixDexFile(this, fixFilePath);
        findViewById(R.id.welcomeIcon).postDelayed(new Runnable() {
            @Override
            public void run() {
                startMian();
            }
        }, 3000);
    }
    private void startMian(){
        startActivity(new Intent(this,MainActivity.class));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x301) {
            startHotFix();
        }
    }
}
