package com.example.androidhotfix;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.example.admin.gamedemo.AuxiliaryService;
import com.gbits.atm.leiting.R;
import com.hotfix.tool.HotFix2;


public class SplashActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
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

        requestOverlayPermission();

    }
    private void startSear(){
        startService(new Intent(this, AuxiliaryService.class));
        findViewById(R.id.welcomeIcon).postDelayed(new Runnable() {
            @Override
            public void run() {
                startMian();
            }
        }, 3000);
    }
    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0x303);
            } else {
                startSear();
            }
        }
    }
    private void startMian(){
        startActivity(new Intent(this,MainActivity.class));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x303){
            startSear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x301) {
            startHotFix();
        }
    }
}
