package com.laomu.note;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.laomu.note.ui.NoteMainFragment;
import com.laomu.note.ui.menu.LeftSlidingMenu;
import com.laomu.note.ui.menu.RightSlidingMenu;
import com.laomu.note.ui.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends SlidingFragmentActivity{

	private String TAG_LEFT = "left";
	private String TAG_RIGHT = "right";
	private String TAG_MAIN = "main";
	private android.support.v4.app.FragmentTransaction frameManager;
	private long mFirstime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		frameManager = getSupportFragmentManager().beginTransaction();
		
		initSlidingMenu(savedInstanceState);
		//添加主视图
		frameManager.replace(R.id.fl_container, new NoteMainFragment(),TAG_MAIN);
		
		//提交事务
		frameManager.commit();
		
	}
	
	private void initUmengTrack() {

		//开启友盟 统计
		MobclickAgent.updateOnlineConfig(this);
		Utils.checkUpdate(this);
		//友盟app版本升级检测模块
		UmengUpdateAgent.update(this);
	}

	private void initSlidingMenu(Bundle savedInstanceState) {
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	
		//左侧menu
		setBehindContentView(R.layout.menu_frame_ono);
		frameManager.replace(R.id.menu_frame_one,new LeftSlidingMenu(),TAG_LEFT);
		
		//fragment into back-stack
//		frameManager.addToBackStack(TAG_LEFT);
		
		//右侧menu
		sm.setSecondaryMenu(R.layout.menu_frame_two);					
		frameManager.replace(R.id.menu_frame_two,new RightSlidingMenu(),TAG_RIGHT);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondtime = System.currentTimeMillis();
			if (secondtime - mFirstime > 3000) {
				Toast.makeText(MainActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
				mFirstime = System.currentTimeMillis();
				return true;
			} else {
				finish();
				System.exit(0);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
