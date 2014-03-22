/**
 * 
 */
package com.laomu.note.ui.act;

import android.app.Activity;
import android.app.Service;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-20
 */
public class CameraNoteActivity extends NoteBaseActivity implements OnClickListener {

	private Camera camera;
	private Camera.Parameters parameters = null;

	Bundle bundle = null; // 声明一个Bundle对象，用来存储数据
	private Button mBtnTakePhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carema_create_note);

		initView();
	}

	private void initView() {
		
		mBtnTakePhoto = (Button)findViewById(R.id.btn_take_photo);
		mBtnTakePhoto.setOnClickListener(this);
		SurfaceView surfaceView = (SurfaceView) this.findViewById(R.id.sv_camera);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setFixedSize(176, 144); // 设置Surface分辨率
		surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
		surfaceView.getHolder().addCallback(new SurfaceCallback());// 为SurfaceView的句柄添加一个回调函数
	}

	private final class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				// bundle = new Bundle();
				// bundle.putByteArray("bytes", data); //
				// 将图片字节数据保存在bundle当中，实现数据交换
				// saveToSDCard(data); // 保存图片到sd卡中
				Toast.makeText(getApplicationContext(), "ss", Toast.LENGTH_SHORT).show();
				camera.startPreview(); // 拍完照后，重新开始预览

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	MyPictureCallback picCallBackRAW = new MyPictureCallback();

	private final class SurfaceCallback implements Callback {

		// 拍照状态变化时调用该方法
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			if (camera != null) {
				parameters = camera.getParameters(); // 获取各项参数
				parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
				parameters.setPreviewSize(width, height); // 设置预览大小
				parameters.setPreviewFrameRate(5); // 设置每秒显示4帧
				parameters.setPictureSize(width, height); // 设置保存的图片尺寸
				parameters.setJpegQuality(80); // 设置照片质量
				parameters.setAutoWhiteBalanceLock(true);
			}
		}

		// 开始拍照时调用该方法
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open(); // 打开摄像头
				camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
				camera.setDisplayOrientation(getPreviewDegree(CameraNoteActivity.this));
				camera.startPreview(); // 开始预览
			} catch (Exception e) {
			}
		}

		// 停止拍照时调用该方法
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
				camera.stopPreview();
				camera.release(); // 释放照相机
				camera = null;
			}
		}
	}

	// 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
	public static int getPreviewDegree(Activity activity) {
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
		return degree;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			takePhotoBtn();
			break;

		default:
			break;
		}
	}

	/**拍照*/
	private void takePhotoBtn() {
		if(camera != null){
			camera.takePicture(shutterCallback, picCallBackRAW, pictureCallbackJPEG );
		}
	}
	
	ShutterCallback shutterCallback = new ShutterCallback() {
		
		@Override
		public void onShutter() {
			
		}
		
		
	};

	 // 照片回调  
	Camera.PictureCallback pictureCallbackJPEG = new Camera.PictureCallback() {  
	    @Override  
	    public void onPictureTaken(byte[] data, Camera camera) {  
//	        new SavePictureTask().execute(data);  
	        camera.startPreview();  
	    }  
	};  
	
}
