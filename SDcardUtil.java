package com.lewa.launcher.fastlane;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SDcardUtil {

    /**
     * check if sdcard exits
     *
     * @return
     */
    public static boolean existSDcard() {
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            return true;
        } else
            return false;
    }

    /**
     * get path
     *
     * @return
     */
    public String getSDPath() {
        File SDdir = null;
        boolean sdCardExist =
                Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            SDdir = Environment.getExternalStorageDirectory();
        }
        if (SDdir != null) {
            return SDdir.toString();
        } else {
            return null;
        }
    }

    /**
     * create folder
     *
     * @return
     */
    public void createSDCardDir(String newPath) {
        if (getSDPath() == null) {
            return;
        } else {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // 创建一个文件夹对象，赋值为外部存储器的目录
                File sdcardDir = Environment.getExternalStorageDirectory();
                //得到一个路径，内容是sdcard的文件夹路径和名字
                newPath = sdcardDir.getPath() + "/***app/tempImages/";//newPath在程序中要声明
                File path1 = new File(newPath);
                if (!path1.exists()) {
                    //若不存在，创建目录，可以在应用启动的时候创建
                    path1.mkdirs();
                    System.out.println("paht ok,path:" + newPath);
                }
            } else {
                System.out.println("false");
            }
        }
    }

    /**
     * save a bitmap to SD
     *
     * @return
     */


    public void saveMyBitmap(String bitName, int percent, Drawable picView ,String newPath ) throws IOException {

        Bitmap bmp = drawable2Bitmap(picView);
        File f = new File(newPath + bitName + ".jpg");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.JPEG, percent, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //change drawable to Bitmap
    static Bitmap drawable2Bitmap(Drawable d) {
        int width = d.getIntrinsicWidth();
        int height = d.getIntrinsicHeight();
        Bitmap.Config config = d.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, width, height);
        d.draw(canvas);
        return bitmap;
    }
}
