package com.example.admin.gamedemo;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import org.cocos2dx.lib.Cocos2dxRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JiPingTool2 {
    JiPingTool2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 60; i++) {
                    loopCapture();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    private void loopCapture(){
        Rect rect=new Rect(0,0,800,800);
        Cocos2dxRenderer.setEnableCapture(true, rect, new Cocos2dxRenderer.CaptureListener() {
            @Override
            public void onBackBitmap(Bitmap bt) {
                Log.e("LLLLLL","onBackBitmap--->");
                saveBitmap(bt);
            }
        });
    }



    public void saveBitmap(final Bitmap bm) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                File f = new File("/sdcard/hotFix/", System.currentTimeMillis() + ".png");
                try {
                    FileOutputStream out = new FileOutputStream(f);
                    bm.compress(Bitmap.CompressFormat.PNG, 60, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//        }).start();
    }
}
