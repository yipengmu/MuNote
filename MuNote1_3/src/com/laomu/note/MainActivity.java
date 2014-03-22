package com.laomu.note;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.laomu.note.ui.NoteMainFragment;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends SlidingFragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		initSlidingMenu(savedInstanceState);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new NoteMainFragment()).commit();
	
		//开启友盟 统计
		MobclickAgent.updateOnlineConfig(this);
	}
	
	private void initSlidingMenu(Bundle savedInstanceState) {
		setBehindContentView(R.layout.left_menu_frame);
		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
}
