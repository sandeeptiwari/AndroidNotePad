package com.sk.notepad.mvp.modle;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Sandeep Tiwari on 7/16/2016.
 */
public class DataBaseReader{
    private static final String TAG = DataBaseReader.class.getSimpleName();
    private Context context;

    public DataBaseReader(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }
}
