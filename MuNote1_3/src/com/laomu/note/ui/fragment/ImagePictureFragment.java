/**
 * 
 */
package com.laomu.note.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.common.camera.ImageManeger;
import com.laomu.note.data.model.ImageModel;
import com.laomu.note.ui.base.NoteBaseFragment;

/**
 * @author luoyuan.myp
 *
 * 2014-5-22
 */
public class ImagePictureFragment extends NoteBaseFragment{

	private Bundle bundleData;
	public static String mTAG = "ImagePictureFragment";
	private String mImagePicturePath = "";
	private ImageModel mImageModel;
	private ImageView mImageView;
	private View mView;

	private TextView mImageTime;
	private TextView mImageLocation;
	private String mTitle = "图片详情";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.image_picture_fragment_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	
		initData();
		initViews();
	}

	private void initViews() {
		findviews();
		setTitle(mView,mTitle);
		initUI();
	}

	private void initUI() {
		if(mImageModel != null && mImageModel.location != null){
			mImageView.setImageBitmap(mImageModel.bitmap);
			mImageTime.setText(mImageModel.time);
			mImageLocation.setText(mImageModel.location.desc);
		}
	}

	private void findviews() {
		mImageView  = (ImageView) mView.findViewById(R.id.iv_image_main);
		mImageTime = (TextView) mView.findViewById(R.id.tv_image_time);
		mImageLocation = (TextView) mView.findViewById(R.id.tv_image_location);
	}

	private void initData() {
		bundleData = getArguments();		
		mImagePicturePath = bundleData.getString("imagePath");
		mImageModel = ImageManeger.getImageModel(mImagePicturePath);
	}
}
