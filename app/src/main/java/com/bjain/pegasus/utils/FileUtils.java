package com.bjain.pegasus.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunil on 26-05-2017.
 */

public class FileUtils {
    public static String BASE_FILE_PATH= Environment.getExternalStorageDirectory().toString()+ File.separator+"pegasus";

    public static void CreateBaseDir(){
        File f=new File(BASE_FILE_PATH);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
