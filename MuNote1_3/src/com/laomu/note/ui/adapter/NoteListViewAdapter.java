/**
 * 
 */
package com.laomu.note.ui.adapter;

import java.util.ArrayList;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.data.NoteBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author luoyuan.myp
 *
 * 2014-2-12
 */
public class NoteListViewAdapter extends BaseAdapter{

	private ArrayList<NoteBean> mData = null;
	private Context mContext;
	
	@Override
	public int getCount() {
		if(mData == null){
			return 0;
		}else{
			return mData.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		ViewHolder holder =null;
		
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(R.layout.note_main_list_item, null);
			holder = new ViewHolder();
			holder.tv_note_info_title = (TextView) view.findViewById(R.id.tv_note_info_title);
			holder.tv_note_time = (TextView) view.findViewById(R.id.tv_note_time);
			holder.tv_weather = (TextView) view.findViewById(R.id.tv_weather);
			holder.tv_location = (TextView) view.findViewById(R.id.tv_location);
			view.setTag(holder); 
		}else{
			holder = (ViewHolder) view.getTag();
		}
		
		drawItemView(pos,mData.get(pos),holder);
		
		return view;
	}

	private void drawItemView(int pos, NoteBean noteBean, ViewHolder holder) {
		MuLog.logi("drawItemView : start  postion =" + pos);
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	public void setDatasource (Context c ,ArrayList<NoteBean> data){
		mData = data;
		mContext = c;
		notifyDataSetChanged();
	}
	class ViewHolder{ 
		TextView tv_note_info_title;
		TextView tv_note_time;
		TextView tv_weather;
		TextView tv_location;
	}
}