/**
 * 
 */
package com.laomu.note.ui.act;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.data.model.CityBean;
import com.laomu.note.ui.adapter.CitySelectionListViewAdapter;
import com.laomu.note.ui.adapter.CitySelectionListViewSectionAdapter;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.widget.CityLetterListView;
import com.laomu.note.ui.widget.OnTouchingLetterChangedListener;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CitySelectionActivity extends NoteBaseActivity{

	private ListView mCityListView;
	private CityLetterListView mCityListViewSection;
	private CitySelectionListViewAdapter mAdatper;
	private CitySelectionListViewSectionAdapter mSectionAdatper;

	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	private TextView overlay;
	private Handler handler = new Handler();
	private OverlayThread overlayThread;
	
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
		arr.add(new CityBean("长沙2","10002"));
		arr.add(new CityBean("四川3","10001"));
		arr.add(new CityBean("达州4","10001"));
		mAdatper.setData(arr);
		
		//字母索引

		initSectionIndexer(arr);
		mCityListViewSection.setOnTouchingLetterChangedListener(new LetterListViewListener());
		
//		mSectionAdatper = new CitySelectionListViewSectionAdapter();
//		mCityListViewSection.setAdapter(mSectionAdatper);
	}

	private void initSectionIndexer(ArrayList<CityBean> list) {
		alphaIndexer = new HashMap<String, Integer>();
		sections = new String[list.size()];
		
		for (int i = 0; i < list.size(); i++)
		{
			String currentStr = list.get(i).getCityName();
			String previewStr = (i - 1) >= 0 ? list.get(i - 1).getCityName() : " ";
			if (!previewStr.equals(currentStr))
			{
				String name = list.get(i).getCityPinyinStart();
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
		}
	}

	private void findViews() {
		mCityListView = (ListView) findViewById(R.id.lv_city_selection);
		mCityListViewSection = (CityLetterListView) findViewById(R.id.lv_city_section);
		overlay = (TextView) findViewById(R.id.overlay);
	}

	private class LetterListViewListener implements OnTouchingLetterChangedListener
	{

		@Override
		public void onTouchingLetterChanged(final String s)
		{
			if (alphaIndexer.get(s) != null)
			{
				int position = alphaIndexer.get(s);
				mCityListView.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}
	

	private class OverlayThread implements Runnable
	{

		@Override
		public void run()
		{
			overlay.setVisibility(View.GONE);
		}

	}
}
