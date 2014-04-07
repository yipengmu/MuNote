package com.laomu.note;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.laomu.note.ui.NoteMainFragment;
import com.laomu.note.ui.menu.LeftSlidingMenu;
import com.laomu.note.ui.menu.RightSlidingMenu;
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
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		FragmentTransaction frameManager = getSupportFragmentManager().beginTransaction();
		//左侧menu
		setBehindContentView(R.layout.menu_frame_ono);
		frameManager.replace(R.id.menu_frame_one,new LeftSlidingMenu());

		//右侧menu
		sm.setSecondaryMenu(R.layout.menu_frame_two);					
		frameManager.replace(R.id.menu_frame_two,new RightSlidingMenu()).commit();
	}
}
