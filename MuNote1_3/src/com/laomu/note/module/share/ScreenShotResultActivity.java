package com.laomu.note.module.share;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * Created by yipengmu on 16/3/15.
 */
public class ScreenShotResultActivity extends NoteBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screenshot_result_layout);

        ImageView img = (ImageView) findViewById(R.id.iv_result);
        Bitmap result = ScreenshotManager.getScreenshotResultBitmap();

        if(result != null){
            img.setImageBitmap(result);
        }

    }
}
