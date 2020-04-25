package com.example.androidhotfix;

import android.content.Context;
import android.widget.Toast;

public class TestBug {
    public TestBug(Context context){
        Toast.makeText(context,"此处有个bug,啦啦啦",Toast.LENGTH_LONG).show();
    }
}
