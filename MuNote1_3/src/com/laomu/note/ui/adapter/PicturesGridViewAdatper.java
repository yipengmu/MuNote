/**
 * 
 */
package com.laomu.note.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.Camera.ImageManeger;
import com.laomu.note.data.model.ImageModel;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-12
 */
public class PicturesGridViewAdatper extends BaseAdapter {

	private List<String> mImagePaths = null;
	private Context mContext;

	@Override
	public int getCount() {
		if (mImagePaths == null) {
			return 0;
		} else {
			return mImagePaths.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return mImagePaths.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		ViewHolder holder = null;

		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.pictures_gridview_item, null);
			holder = new ViewHolder();
			holder.iv_grid_item_img = (ImageView) view.findViewById(R.id.iv_grid_item_img);
			holder.tv_grid_item_text = (TextView) view.findViewById(R.id.tv_grid_item_text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		drawItemView(pos, mImagePaths.get(pos), holder);

		return view;
	}

	private void drawItemView(int pos, String filedir, ViewHolder holder) {
		MuLog.logd("drawItemView : start  postion =" + pos);
		ImageModel imageModel = loadDrawableImage(pos);
		holder.iv_grid_item_img.setImageBitmap(imageModel.bitmap);
		holder.tv_grid_item_text.setText(imageModel.name);
	}

	public ImageModel loadDrawableImage(int pos) {
		return ImageManeger.getImageModel(mImagePaths.get(pos));
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}

	public void setDatasource(Context c, List<String> data) {
		mImagePaths = data;
		mContext = c;
		notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView iv_grid_item_img;
		TextView tv_grid_item_text;
	}
}
