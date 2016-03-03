package com.laomu.note.common.screenshot;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenshotManager {
    private static Bitmap screenShotBitmap;

    public static Bitmap getScreenShotBitmap() {
        return screenShotBitmap;
    }

    public static void setScreenShotBitmap(Bitmap screenShotBitmap) {
        ScreenshotManager.screenShotBitmap = screenShotBitmap;
    }

    /**
     * 保存图片
     */
    public boolean saveToSDCard(Bitmap bitmap,String filePath) {
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
