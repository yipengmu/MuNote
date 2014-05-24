/**
 * 
 */
package com.laomu.note.ui.act.ad;

import android.os.Bundle;
import android.widget.TextView;

import com.j.p.api.PopManager;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 *
 * 2014-5-24
 */
public class JumiAdActivity extends NoteBaseActivity {

    PopManager manager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		manager = PopManager.getInstance(getApplicationContext(),"9906a7e0-1578-4f06-8fb7-23bfe710a3bb", 1);
        // 配置插屏，开启外插屏
        manager.c(getApplicationContext(), 1, 3, true, true);
        // 配置外插屏
        manager.o(getApplicationContext(), false, 4, true, true, true);
        TextView tv = new TextView(this);
        tv.setText("测试ad");
        setContentView(tv);
        manager.s(this);
	}
}
