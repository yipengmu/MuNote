/**
 * 
 */
package com.laomu.note.common.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-20
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.PictureCallback {

	private SurfaceHolder holder;
	private Camera camera;
	private boolean af;
	private static PictureCallback mPictureCallback;
	private Activity mActivity;
//	private int prepareCameraKachaId = -1;

	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CameraSurfaceView(Context context) {// 构造函数
		super(context);
		init();
	}

	private void init() {
		holder = getHolder();// 生成Surface Holder
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 指定Push Buffer
	}

	public void setActivity(Activity mAct) {
		mActivity = mAct;
	}

	public void surfaceCreated(SurfaceHolder holder) {// Surface生成事件的处理
		try {
			camera = Camera.open();// 摄像头的初始化
			camera.setPreviewDisplay(holder);
			camera.startPreview();
			ImageManeger.setPreviewDegree(mActivity, camera);
//			prepareCameraKachaId = Utils.prepareCameraKacha(mActivity);

		} catch (Exception e) {
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {// Surface改变事件的处理
	// camera.startPreview();// 开始预览
	}

	public void surfaceDestroyed(SurfaceHolder holder) {// Surface销毁时的处理
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// 屏幕触摸事件
		if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下时自动对焦
			camera.autoFocus(null);
			Toast.makeText(mActivity, "正在对焦..", Toast.LENGTH_SHORT).show();
			af = true;
		}
		return true;
	}

	public void takePicture() {
		camera.takePicture(null, null, this);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		try {
//			if(prepareCameraKachaId != -1){
//				Utils.startCameraKacha(mActivity,prepareCameraKachaId);
//			}
			saveToSDCard(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToSDCard(byte[] data) throws IOException {

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		String fileName = "munote" + System.currentTimeMillis() + ".jpg";
		File pathDir = new File(Environment.getExternalStorageDirectory() + "/munote/pics");
		if (!pathDir.exists()) {
			pathDir.mkdirs();
		}

		Toast.makeText(mActivity, "图片已保存在： " + pathDir.getAbsolutePath() + fileName,
				Toast.LENGTH_SHORT).show();

		File file = new File(pathDir, fileName);
		FileOutputStream outStream = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, outStream);
		outStream.close();// 重新浏览
	}

}