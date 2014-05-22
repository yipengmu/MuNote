/**
 * 
 */
package com.laomu.note.common.Camera;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.laomu.note.ui.util.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.view.Surface;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-20
 */
public class CameraManeger {
	private static LruCache<String, Bitmap> mImageCacheLru = new LruCache<String, Bitmap>(50);

	public static LruCache<String, Bitmap> getImageLruCache() {
		return mImageCacheLru;
	}

	public static Bitmap getImage(String filePath) {
		Bitmap image = mImageCacheLru.get(filePath);
		if (image == null) {
			setImageCache(filePath);
		}
		return mImageCacheLru.get(filePath);
	}

	private static void setImageCache(String filePath) {
		Bitmap image = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		opts.inSampleSize = Utils.computeInitialSampleSize(opts, 600, 128 * 128);
		opts.inJustDecodeBounds = false;
		image = BitmapFactory.decodeFile(filePath, opts);
		// 将当前filepath对应的图片加载到lrucache中
		mImageCacheLru.put(filePath, image);
	}

	public static void setImagesLruCache(List<String> imageFiles) {
		for(int i =0;i<imageFiles.size();i++){
			Bitmap image = mImageCacheLru.get(imageFiles.get(i));
			if (image == null) {
				setImageCache(imageFiles.get(i));
			}
		}
	}

	public static String getExternPicturecDir() {
		return Environment.getExternalStorageDirectory() + "/munote/pics";
	}

	// 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
	public static void setPreviewDegree(Activity activity, Camera camera) {
		// 获得手机的方向
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degree = 0;
		// 根据手机的方向计算相机预览画面应该选择的角度
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 90;
			break;
		case Surface.ROTATION_90:
			degree = 0;
			break;
		case Surface.ROTATION_180:
			degree = 270;
			break;
		case Surface.ROTATION_270:
			degree = 180;
			break;
		}
		camera.setDisplayOrientation(degree);
	}

	public static String[] getNotePictrueFiles() {
		String[] filesList = null;
		File pathDir = new File(CameraManeger.getExternPicturecDir());
		if (pathDir.exists()) {
			filesList = pathDir.list();
		}
		return filesList;
	}
}
