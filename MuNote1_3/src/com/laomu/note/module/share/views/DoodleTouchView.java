package com.laomu.note.module.share.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.laomu.note.common.MuLog;
import com.laomu.note.module.share.ScreenshotManager;
import com.laomu.note.module.share.listener.SealTextClickListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DoodleTouchView extends ImageView {
    private Bitmap bitmapBackgroud;
    private Bitmap bitmapDoodle;
    private Bitmap bitmapSeal;
    private SSEditText mSSEditText;

    private Canvas mCanvas;
    private Path mPath;
    private Paint mDoodlePaint;
    private float mX, mY;// 临时点坐标
    private static final float TOUCH_TOLERANCE = 4;


    // 保存Path路径的集合,用List集合来模拟栈
    private static List<DoodleDrawPath> savePath;
    // 记录Path路径的对象
    private DoodleDrawPath dp;

    private boolean isInit = false;// 用来标记保证只被初始化一次

    // 布局高和宽
    private float screenWidth = 500, screenHeight = 500;

    // 文案编辑框起始X，起始Y
    private float mSealRectStartX = 0, mSealRectStartY = 0;

    private SealRectHolder mSealrectHolder;
    private SealTextClickListener mSealTextClickListener;

    public DoodleTouchView(Context context) {
        super(context);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public DoodleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public DoodleTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public void init(float w, float h) {
        // 保证只被初始化一次就够了
        if (!isInit) {
            screenWidth = w;
            screenHeight = h;

            bitmapBackgroud = Bitmap.createBitmap((int)screenWidth, (int)screenHeight,
                    Bitmap.Config.ARGB_8888);

            // 保存一次一次绘制出来的图形
            mCanvas = new Canvas(bitmapBackgroud);


            mDoodlePaint = new Paint();
            mDoodlePaint.setAntiAlias(true);
            mDoodlePaint.setStyle(Paint.Style.STROKE);
            mDoodlePaint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
            mDoodlePaint.setStrokeCap(Paint.Cap.ROUND);// 形状
            mDoodlePaint.setStrokeWidth(5);// 画笔宽度

            savePath = new ArrayList<DoodleDrawPath>();
            isInit = true;
            // 保持一个path,支持多次手势涂鸦重叠
            mPath = new Path();

            try {
                bitmapSeal = BitmapFactory.decodeStream(new FileInputStream(ScreenshotManager.getSreenShotFilePath(null)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            mSealrectHolder = new SealRectHolder();


            mSealRectStartX = getX();
            mSealRectStartY = getTop();


            MuLog.logd("mSealRectStartX= " + mSealRectStartX);
            MuLog.logd("mSealRectStartY= " + mSealRectStartY);

        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        //盖章文案绘制
        if (bitmapSeal != null) {
            canvas.drawBitmap(bitmapSeal, mSealRectStartX, mSealRectStartY, null);
        }

        // 涂鸦绘制
        if (mPath != null) {
            canvas.drawPath(mPath, mDoodlePaint);
        }

    }

    // 布局的大小改变时，就会调用该方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 布局的大小改变时，就会调用该方法，在这里获取到view的高和宽
        screenWidth = w;
        screenHeight = h;

        // view初始化
        init(screenWidth, screenHeight);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 撤销上一步画线<br />
     * 撤销的核心思想就是将画布清空，将保存下来的Path路径最后一个移除掉，重新将路径画在画布上面。
     */
    public void undo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.remove(savePath.size() - 1);
            redrawOnBitmap();
        }
    }

    /**
     * 重做,就是清空所有的画线<br />
     * 核心思想就是，清空Path路径后，进行重新绘制
     */
    public void redo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.clear();
            redrawOnBitmap();
        }
    }

    // 按下
    private void touch_start(float x, float y) {

        // 每一次记录的路径对象是不一样的
        dp = new DoodleDrawPath();
        dp.path = mPath;
        dp.paint = mDoodlePaint;

        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // 正在滑动中
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(mY - y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // 从（mX,mY）到（x,y）画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也是可以的)
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    // 滑动太手
    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mDoodlePaint);
        // 将一条完整的路径保存下来(相当于入栈操作)
        savePath.add(dp);
//		mPath = null;// 重新置空
    }

    // 重新绘制Path中的画线路径
    private void redrawOnBitmap() {
        bitmapBackgroud = Bitmap.createBitmap((int)screenWidth, (int)screenHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(bitmapBackgroud);// 重新设置画布，相当于清空画布

        for (DoodleDrawPath drawPath : savePath) {
            mCanvas.drawPath(drawPath.path, drawPath.paint);
        }

        invalidate();// 刷新
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                checkSealTextClick(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                break;
        }

        invalidate();
        return true;
    }

    private void checkSealTextClick(float x, float y) {
        if (mSealTextClickListener == null) {
            return;
        }

        //判断当前按下触发的是否是落在编辑框区域中
        //判断当前按下触发的是否是落在编辑框区域中
        if (x > 0 && x < mSealrectHolder.getSealWidth() + mSealrectHolder.bitmapSeal.getWidth() &&
                y > mSealrectHolder.getSealHeight() && y < mSealrectHolder.getSealHeight() + mSealrectHolder.bitmapSeal.getHeight()) {

            //如果在区域内，则进入此逻辑：隐藏sealbitmap,展示EditText标准编辑框
            mSealTextClickListener.onTextRectInSideClick();

        } else if (x > 0 && x < mSealrectHolder.getSealWidth() + mSealrectHolder.bitmapSeal.getWidth() && (y > 0 && y < mSealrectHolder.getSealHeight() || (y > mSealrectHolder.getSealHeight() + mSealrectHolder.bitmapSeal.getHeight())) ||
                y > mSealrectHolder.getSealHeight() && ((x > 0 && x < mSealrectHolder.getSealWidth()) || (x > mSealrectHolder.getSealWidth() + mSealrectHolder.bitmapSeal.getWidth()))) {

            //如果在区域外，则进入此逻辑：隐藏sealbitmap,展示EditText标准编辑框
            mSealTextClickListener.onTextRectOutSideClick();

            //隐藏seal区域bitmap绘制
//            bitmapSeal = null;
        }
    }

    public void setImageBackgroud(Bitmap bitmap) {
        bitmapBackgroud = bitmap;
    }

    public void setSealTextBitmap(Bitmap editTextUiBitmap) {
        bitmapSeal = editTextUiBitmap;
    }

    public void setTextClickListener(SealTextClickListener listener) {
        mSealTextClickListener = listener;
    }

    public void setSealTextLayout(float startX,float startY){

        mSealRectStartX = startX;
        mSealRectStartY = startY;

        invalidate();
    }

}
