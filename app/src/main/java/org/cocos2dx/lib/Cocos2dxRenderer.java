//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.cocos2dx.lib;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLException;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Cocos2dxRenderer implements Renderer {
    private static final long NANOSECONDS5MICROSECOND = 5000000L;
    private static final long NANOSECONDSPERMICROSECOND = 1000000L;
    private static final long NANOSECONDSPERSECOND = 1000000000L;
    private static long sAnimationInterval = 16666666L;
    private long mLastTickInNanoSeconds;
    private int mScreenHeight;
    private int mScreenWidth;

    public Cocos2dxRenderer() {
    }

    private static native void nativeDeleteBackward();

    private static native String nativeGetContentText();

    private static native void nativeInit(int var0, int var1);

    private static native void nativeInsertText(byte[] var0, int var1);

    private static native boolean nativeKeyDown(int var0);

    private static native void nativeOnPause();

    private static native void nativeOnResume();

    private static native void nativeOnSurfaceChanged(int var0, int var1);

    private static native void nativeRender();

    private static native void nativeTouchesBegin(int var0, float var1, float var2);

    private static native void nativeTouchesCancel(int[] var0, float[] var1, float[] var2);

    private static native void nativeTouchesEnd(int var0, float var1, float var2);

    private static native void nativeTouchesMove(int[] var0, float[] var1, float[] var2);

    public static void setAnimationInterval(double var0) {
        sAnimationInterval = (long)(1.0E9D * var0);
    }

    public String getContentText() {
        return nativeGetContentText();
    }

    public void handleActionCancel(int[] var1, float[] var2, float[] var3) {
        nativeTouchesCancel(var1, var2, var3);
    }

    public void handleActionDown(int var1, float var2, float var3) {
        nativeTouchesBegin(var1, var2, var3);
    }

    public void handleActionMove(int[] var1, float[] var2, float[] var3) {
        nativeTouchesMove(var1, var2, var3);
    }

    public void handleActionUp(int var1, float var2, float var3) {
        nativeTouchesEnd(var1, var2, var3);
    }

    public void handleDeleteBackward() {
        nativeDeleteBackward();
    }

    public void handleInsertText(String var1) {
        Log.e("LLLLLL","handleInsertText--->"+var1);
        UnsupportedEncodingException var10000;
        label37: {
            boolean var10001;
            int var4;
            StringBuilder var5;
            var5 = new StringBuilder();
            var4 = var1.length();

            int var3 = 0;

            while(true) {
                if (var3 >= var4) {
                    try {
                        byte[] var10 = var5.toString().getBytes("UTF-8");
                        nativeInsertText(var10, var10.length);
                        return;
                    } catch (UnsupportedEncodingException var6) {
                        var10000 = var6;
                        var10001 = false;
                        break;
                    }
                }

                char var2 = var1.charAt(var3);
                if (Cocos2dxHelper.isEmojiCharacter(var2)) {
                    var5.append(var2);
                }

                ++var3;
            }
        }

        UnsupportedEncodingException var9 = var10000;
        var9.printStackTrace();
    }

    public void handleKeyDown(int var1) {
        nativeKeyDown(var1);
    }

    public void handleOnPause() {
        Cocos2dxHelper.onEnterBackground();
        nativeOnPause();
    }
    public void setScreenWidthAndHeight(int var1, int var2) {
        this.mScreenWidth = var1;
        this.mScreenHeight = var2;
    }

    public void handleOnResume() {
        Cocos2dxHelper.onEnterForeground();
        nativeOnResume();
    }

    public void onDrawFrame(GL10 var1) {
        Log.e("LLLLLL","onDrawFrame-111-->"+enableCapture);

        if ((double)sAnimationInterval <= 1.6666666666666666E7D) {
            nativeRender();
            Log.e("LLLLLL","onDrawFrame-444-->"+enableCapture);
        } else {
            if (System.nanoTime() - this.mLastTickInNanoSeconds < sAnimationInterval) {
                while(true) {
                    try {
                        if (System.nanoTime() - this.mLastTickInNanoSeconds >= sAnimationInterval - 5000000L) {
                            break;
                        }

                        Thread.sleep(5L);
                    } catch (Exception var2) {
                        break;
                    }
                }
            }
            Log.e("LLLLLL","onDrawFrame-333-->"+enableCapture);
            this.mLastTickInNanoSeconds = System.nanoTime();
            nativeRender();
        }
        Log.e("LLLLLL","onDrawFrame-222-->"+enableCapture);
        if(enableCapture){
            enableCapture=false;
            doLoop(var1);
        }
    }

    public void onSurfaceChanged(GL10 var1, int var2, int var3) {
        nativeOnSurfaceChanged(var2, var3);
    }

    public void onSurfaceCreated(GL10 var1, EGLConfig var2) {
        nativeInit(this.mScreenWidth, this.mScreenHeight);
        this.mLastTickInNanoSeconds = System.nanoTime();
    }

    static  boolean enableCapture;
    static Rect captureRect;
    static CaptureListener captureListener;
    public static void setEnableCapture(boolean enable, Rect rect,CaptureListener listener){
        enableCapture=enable;
        captureRect=rect;
        captureListener=listener;
    }
    private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl) {
        Log.e("LLLLLL","createBitmapFromGLSurface--->"+1111);
        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);
        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
                    intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j <w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("LLLLLL","createBitmapFromGLSurface--->"+e);
            return null;
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.RGB_565);
    }
    public interface CaptureListener{
        void onBackBitmap(Bitmap bt);
    }
    private void doLoop(final GL10 gl){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=createBitmapFromGLSurface(captureRect.left,captureRect.top,captureRect.right,captureRect.bottom,gl);
                captureListener.onBackBitmap(bitmap);
            }
        });
    }
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
}
