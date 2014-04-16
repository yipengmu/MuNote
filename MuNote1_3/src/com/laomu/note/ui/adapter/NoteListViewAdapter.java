/**
 * 
 */
package com.laomu.note.ui.adapter;

import java.util.ArrayList;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.data.model.NoteBean;

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
			holder.tv_note_info_title = (TextView) view.findViewById(R.id.tv_note_title);
			holder.tv_note_info = (TextView) view.findViewById(R.id.tv_note_info);
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
		MuLog.logd("drawItemView : start  postion =" + pos);
		if(null != noteBean.note_time){
			holder.tv_note_time.setText(noteBean.note_time);
		}
		if(null != noteBean.note_title){
			holder.tv_note_info_title.setText(noteBean.note_title);
		}
		if(null != noteBean.note_content){
			holder.tv_note_info.setText(noteBean.note_content);
		}
//		if(null != noteBean.note_location){
//			holder.tv_location.setText(noteBean.note_location.getDesc());
//		}
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
		TextView tv_note_info;
		TextView tv_note_time;
		TextView tv_weather;
		TextView tv_location;
	}
}
