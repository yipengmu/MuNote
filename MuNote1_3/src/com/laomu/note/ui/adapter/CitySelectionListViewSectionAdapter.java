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
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.util.Utils;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CitySelectionListViewSectionAdapter extends BaseAdapter {

	private ArrayList<String> mListData = new ArrayList<String>();
	private Context mContext;
	private LayoutInflater inflater;
	private String[] letters = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	public CitySelectionListViewSectionAdapter(){
		mContext = NoteApplication.appContext;
		addSectionList(letters);
	}
	
	private void addSectionList(String[] letters2) {
		mListData.clear();
		for(String l:letters){
			mListData.add(l);
		}
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
			convertView = inflater.inflate(R.layout.city_selection_section_item, parent,false);
		
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
		
		holder.tvMiddle.setText(mListData.get(position));
	}

	class ViewHolder {
		TextView tvMiddle;
	}
}
