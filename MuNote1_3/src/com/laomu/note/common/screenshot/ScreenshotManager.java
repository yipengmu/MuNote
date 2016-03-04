package com.laomu.note.common.screenshot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.laomu.note.ui.NoteApplication;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenshotManager {
    private static Bitmap screenShotBgBitmap;
    private static Bitmap editTextUIBitmap;

    public static Bitmap getscreenShotBgBitmap() {
        return screenShotBgBitmap;
    }

    public static void setscreenShotBgBitmap(Bitmap screenShotBitmap) {
        ScreenshotManager.screenShotBgBitmap = screenShotBitmap;
    }

    public static Bitmap getEditTextUIBitmap() {
        return editTextUIBitmap;
    }

    public static void setEditTextUIBitmap(Bitmap editTextUIBitmap) {
        ScreenshotManager.editTextUIBitmap = editTextUIBitmap;
    }


    /**
     * 保存图片
     */
    public static boolean saveScreenShotToSDCard(Bitmap bitmap) {
        return saveToSDCard(bitmap, getSreenShotFilePath());
    }

    public static String getSreenShotFilePath() {
//        return  NoteApplication.appContext.getPackageResourcePath()+
//                File.separator + "sreenshot" + File.separator + "ss.png";

        return  "/sdcard/react/ss1.png";
    }

    public static Bitmap getBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.draw(c);
        return screenshot;
    }

    /**
     * 保存图片
     */
    public static boolean saveToSDCard(Bitmap bitmap,String filePath) {
        try {
            File file = new File(filePath);
            createParentDirs(file);

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // FileUtils.saveFileByBitmap(filePath, mBitmap);
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    // 如果父目录不存在，则创建之
    private static void createParentDirs(File file) {
        File parentPath = file.getParentFile();
        if (!parentPath.exists() || !parentPath.isDirectory()) {
            parentPath.mkdirs();
        }
    }
}
