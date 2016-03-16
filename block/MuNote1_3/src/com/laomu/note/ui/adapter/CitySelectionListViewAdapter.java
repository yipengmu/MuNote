/**
 * 
 */
package com.laomu.note.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.data.model.CityBean;
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.util.Utils;
import com.laomu.note.ui.widget.OnTouchingLetterChangedListener;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CitySelectionListViewAdapter extends BaseAdapter implements OnTouchingLetterChangedListener{

	private ArrayList<CityBean> mListData;
	private Context mContext;
	private LayoutInflater inflater;
	
	public CitySelectionListViewAdapter(){
		mContext = NoteApplication.appContext;
	}
	
	public void setData(ArrayList<CityBean> data){
		mListData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mListData == null?0:mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData == null?null:mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Utils.logd("pos=" + position);
		ViewHolder holder = new ViewHolder();
		if(convertView == null){
			if(inflater == null){
				inflater = LayoutInflater.from(mContext);
			}
			convertView = inflater.inflate(R.layout.city_selection_item, parent,false);
		
			holder.tvMiddle = (TextView) convertView.findViewById(R.id.tv_item_middle);
			convertView.setTag(holder);
			initItemUI(position,holder,convertView,true);
		}else{
			holder = (ViewHolder) convertView.getTag();
			initItemUI(position,holder,convertView,false);
		}
		
		return convertView;
	}

	private void initItemUI(int position,ViewHolder holder, View convertView,boolean bBindUIWidget) {
		if(!bBindUIWidget){
			holder.tvMiddle = (TextView) convertView.findViewById(R.id.tv_item_middle);
		}
		
		holder.tvMiddle.setText(mListData.get(position).getCityName());
	}

	class ViewHolder {
		TextView tvMiddle;
		
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		
	}
}
