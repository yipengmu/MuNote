/**
 * 
 */
package com.laomu.note.ui.loadingview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.laomu.note.R;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-22
 */
public class NoteLoadingView extends ViewGroup {
	private ImageView mLoadingImageView;
	private Animation mLoadingAnimation;

	public NoteLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NoteLoadingView(Context context) {
		super(context);
		init();
	}

	private void init() {
		View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, this);
		mLoadingImageView = (ImageView) loadingView.findViewById(R.id.iv_loading_img);
	}

	public void show() {
		mLoadingAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_view_anim);
		mLoadingImageView.startAnimation(mLoadingAnimation);
	}

	public void dismiss() {
		mLoadingAnimation.cancel();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

}