/**
 * 
 */
package com.laomu.note.ui.act.camera;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.laomu.note.R;
import com.laomu.note.common.Camera.CameraSurfaceView;
import com.laomu.note.common.Camera.DataLoadThread;
import com.laomu.note.common.Camera.ImageManeger;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-20
 */
public class CameraNoteActivity extends NoteBaseActivity implements OnClickListener {

	private Button mBtnTakePhoto;
	private ImageView mImageView;
	private CameraSurfaceView surfaceView;
	private Thread mDataLoadThread ; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carema_create_note);

		initView();
		// 初始化左下图片卡片集
		initPictureCards();
		loadImageData();
	}

	//后台线程启动去加载munote的对应图片
	private void loadImageData() {
		mDataLoadThread = new Thread(new DataLoadThread(getPicturesFilePath())); 
		mDataLoadThread.start();
	}

	private void initPictureCards() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				String[] filePath = ImageManeger.getNotePictrueFiles();
				if (filePath == null || filePath.length == 0) {
					return;
				}

				String pictureFile = ImageManeger.getExternPicturecDir() + File.separator
						+ filePath[filePath.length - 1];
			
				mImageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_in));
				mImageView.setImageBitmap(ImageManeger.getImage(pictureFile));
				mImageView.setVisibility(View.VISIBLE);
				}
		},40);
	}

	public List<String> getPicturesFilePath(){

		String[] filePath = ImageManeger.getNotePictrueFiles();
		List<String> paths = new ArrayList<String>();
		String dirPath = ImageManeger.getExternPicturecDir() + File.separator;
		if(filePath != null){
			for(int i =0;i<filePath.length;i++){
				paths.add(dirPath + filePath[i]);
			}
		}
		return paths;
	}
	private void initView() {

		mBtnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
		mBtnTakePhoto.setOnClickListener(this);
		mImageView = (ImageView) findViewById(R.id.iv_pic_preview);
		mImageView.setOnClickListener(this);

		surfaceView = (CameraSurfaceView) this.findViewById(R.id.sv_camera);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setFixedSize(176, 144); // 设置Surface分辨率
		surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
		surfaceView.setActivity(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			takePhotoBtn();
			break;
		case R.id.iv_pic_preview:
			intoPicturesGridActivity();
			break;
		default:
			break;
		}
	}

	private void intoPicturesGridActivity() {
		Intent intent = new Intent(this, CameraPicturesActivity.class);
		intent.putStringArrayListExtra("filePaths", (ArrayList)getPicturesFilePath());
		startActivity(intent);
	}

	private void takePhotoBtn() {
		surfaceView.takePicture();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mDataLoadThread !=null){
			mDataLoadThread.interrupt();
		}
	}

}
