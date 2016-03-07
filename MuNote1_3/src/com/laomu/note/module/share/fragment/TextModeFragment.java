package com.laomu.note.module.share.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.screenshot.ScreenshotManager;
import com.laomu.note.common.screenshot.view.SealTextClickListener;
import com.laomu.note.module.share.views.SealTouchView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextModeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TextModeFragment extends Fragment  implements SealTextClickListener {

    private Button mBtnAddText;
    private EditText mEtTagText;
    private OnFragmentInteractionListener mListener;
    private ViewGroup rl_screenshot_textedit_container;
    private Bitmap mEditTextUiBitmap;
    private SealTouchView mSealTouchView;

    public TextModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_mode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(), "TextModeFragment show", Toast.LENGTH_SHORT).show();

        initView(view);
    }

    private void initView(View view) {

        mBtnAddText = (Button) view.findViewById(R.id.btn_add_text);
        mEtTagText = (EditText) view.findViewById(R.id.et_tag_text);
        rl_screenshot_textedit_container = (ViewGroup) view.findViewById(R.id.rl_screenshot_textedit_container);
        mSealTouchView = (SealTouchView) view.findViewById(R.id.seal_touch_view);

        mBtnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_screenshot_textedit_container.setClickable(true);
                mEtTagText.setVisibility(View.VISIBLE);
            }
        });

        rl_screenshot_textedit_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSealTouchView.isShown()){
                    //退出文案贴图编辑态
                    mSealTouchView.setVisibility(View.GONE);
                    mEtTagText.setVisibility(View.VISIBLE);
                }else {
                    //退出输入框输入态
                    mSealTouchView.setVisibility(View.VISIBLE);
                    mEtTagText.setVisibility(View.GONE);
                    handleEditTextDrawingCacheBitmap(mEtTagText);
                }
            }
        });

        rl_screenshot_textedit_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                MuLog.logd("container onTouchEvent event.getX()=" + event.getX() + " event.getY()=" + event.getY());
                return false;
            }
        });

        mSealTouchView.setTextClickListener(this);
    }

    private void handleEditTextDrawingCacheBitmap(EditText editText) {
        mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(editText);

        if(mEditTextUiBitmap != null){
            paustEditTextDrawingCacheBitmapToTouchView(mEditTextUiBitmap);
        }

    }

    private void paustEditTextDrawingCacheBitmapToTouchView(Bitmap editTextUiBitmap) {
        mSealTouchView.setVisibility(View.VISIBLE);
        mSealTouchView.setSealBitmap(editTextUiBitmap);
        ScreenshotManager.setEditTextUIBitmap(editTextUiBitmap);
        mSealTouchView.invalidate();
    }

    private void initSSEditText() {

        mEtTagText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    mSealTouchView.setVisibility(View.GONE);
                }
            }
        });

        //获取drawingcache后隐藏输入框，方便后续screenshotView 做touch动作
        mEtTagText.post(new Runnable() {
            @Override
            public void run() {
                mEtTagText.setDrawingCacheEnabled(true);
//                mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(mEtTagText);
//
//                if (mEditTextUiBitmap != null) {
//                    ScreenshotManager.setEditTextUIBitmap(mEditTextUiBitmap);
//
//                    ScreenshotManager.saveScreenShotToSDCard(mEditTextUiBitmap, mEditTextTagBitmapFileName);
//                    mScreenshotView.invalidate();
//                }

                mEtTagText.setVisibility(View.GONE);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onTextRectOutSideClick() {

        MuLog.logd("onTextRectOutSideClick");
        mEtTagText.setVisibility(View.GONE);
    }


    @Override
    public void onTextRectInSideClick() {
        MuLog.logd("onTextRectInSideClick");
        mEtTagText.setVisibility(View.VISIBLE);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
