/**
 * 
 */
package com.laomu.note.ui.act;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.fragment.MapNoteFragment;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-27
 */

public class MapNoteActivity extends NoteBaseActivity {

	private FragmentTransaction frameManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		initViews();
	}

	private void initViews() {
		frameManager = getSupportFragmentManager().beginTransaction();
		// 添加主视图
		frameManager.replace(R.id.fl_container, new MapNoteFragment(), "map");
		// 提交事务
		frameManager.commit();

	}

}
