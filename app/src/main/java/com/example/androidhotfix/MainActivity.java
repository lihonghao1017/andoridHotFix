package com.example.androidhotfix;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.admin.gamedemo.JiPingTool2;
import com.gbits.atm.leiting.R;

import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    View fixIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TestBug(this);
        checkPermission();
        fixIcon=findViewById(R.id.fixIcon);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                click();
            }
        }).start();

    }
    public void  click(){
//        fixIcon.post(new Runnable() {
//            @Override
//            public void run() {
//        int x = 0, y = 0;
//        String[] order = { "input", "tap", " ", x + "", y + "" };
//        try {
//            new ProcessBuilder(order).start();
//            Log.e("LLLLLL", order.toString());
//        } catch (IOException e) {
//            Log.e("LLLLLL", e.getMessage());
//            e.printStackTrace();
//        }


                try {
                    int x=200,y=200;
                    Instrumentation inst = new Instrumentation();
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0));
                    Log.i("LLLLLL", "模拟点击" + x + ", " + y);
                } catch (Exception e) {
                    Log.e("LLLLLL", e.toString());
                }
//        .

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("LLLLLL", event.getX()+"--"+event.getY());
        return super.onTouchEvent(event);
    }

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private void checkPermission() {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 0x301);
            } else {
                doLuping();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLuping() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {//响应Code
            case 0x031:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    doLuping();
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this, "未拥有相应权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
private JiPingTool2 jiPingTool;
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (jiPingTool != null) jiPingTool.release();
    }
}
