/**
 * 
 */
package com.laomu.note.ui.act;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.laomu.note.R;
import com.laomu.note.data.model.CityBean;
import com.laomu.note.ui.adapter.CitySelectionListViewAdapter;
import com.laomu.note.ui.adapter.CitySelectionListViewSectionAdapter;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CitySelectionActivity extends NoteBaseActivity{

	private ListView mCityListView;
	private ListView mCityListViewSection;
	private CitySelectionListViewAdapter mAdatper;
	private CitySelectionListViewSectionAdapter mSectionAdatper;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_selection_layout);
		initViews();
	}

	private void initViews() {
		findViews();

		setTitle(0, R.string.city_list, 0);

		initListView();
	}

	private void initListView() {
		mAdatper = new CitySelectionListViewAdapter();
		mCityListView.setAdapter(mAdatper);
		ArrayList<CityBean> arr = new ArrayList<CityBean>();
		arr.add(new CityBean("北京","10000"));
		arr.add(new CityBean("西安1","10001"));
		arr.add(new CityBean("西安2","10002"));
		arr.add(new CityBean("西安3","10001"));
		arr.add(new CityBean("西安4","10001"));
		mAdatper.setData(arr);
		
		//字母索引
		mSectionAdatper = new CitySelectionListViewSectionAdapter();
		mCityListViewSection.setAdapter(mSectionAdatper);
	}

	private void findViews() {
		mCityListView = (ListView) findViewById(R.id.lv_city_selection);
		mCityListViewSection = (ListView) findViewById(R.id.lv_city_section);
	}
}
