package com.example.androidhotfix;

import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Textooc {
    private static final String TESSBASE_PATH = "/sdcard/hotFix/";
    private static final String DEFAULT_LANGUAGE = "eng";
    private static final String CHINESE_LANGUAGE = "chi_sim";
    private static final String TAG = "tesseract";
//    static TessBaseAPI baseApi;
//    static {
//         baseApi=new TessBaseAPI();
//        baseApi.init(TESSBASE_PATH, CHINESE_LANGUAGE);
//        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
//    }
    static void re(Bitmap bitmap){
        TessBaseAPI baseApi=new TessBaseAPI();
        baseApi.init(TESSBASE_PATH, CHINESE_LANGUAGE);
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        baseApi.setImage(bitmap);
        final String outputText = baseApi.getUTF8Text();
        Log.i(TAG, "识别结果：" + outputText);
        baseApi.end();
    }
}
