package com.laomu.note.common.speech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.EditText;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.laomu.note.R;
import com.laomu.note.ui.util.Utils;

public class SpeechManeger {
	private static Context mContext;
	private static String TAG = "IatDemo";
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog iatDialog;
	// 听写结果内容
	private EditText mResultText;
	private SharedPreferences mSharedPreferences;
	private String PREFER_NAME = "note_prefer_name";
	int ret = 0;// 函数调用返回值
	private static SpeechManeger mSpeechManeger;
	private boolean bInitCompleted = false;
	private Handler mMsgHandler;
	
	public static SpeechManeger getInstance(Context c) {
		mContext = c;
		if (mSpeechManeger == null) {
			mSpeechManeger = new SpeechManeger();
		}
		return mSpeechManeger;
	}

	
	public void setMsgHandler(Handler msgHandler){
		mMsgHandler = msgHandler;
	}
	
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code == ErrorCode.SUCCESS) {
				bInitCompleted = true;
			}
		}

	};

	private SpeechManeger() {
		mSharedPreferences = mContext.getSharedPreferences(PREFER_NAME,
				Activity.MODE_PRIVATE);
	}

	public void init() {
		mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(mContext, mInitListener);
	}

	public void showSpeechDialog() {
		if (!bInitCompleted) {
			Utils.toast("语音系统初始化失败 。。");
			return;
		}

		setParam();
		boolean isShowDialog = mSharedPreferences.getBoolean(
				mContext.getString(R.string.pref_key_iat_show), true);
		if (isShowDialog) {
			// 显示听写对话框
			iatDialog.setListener(recognizerDialogListener);
			iatDialog.show();
		} else {
			// 不显示听写对话框
			ret = mIat.startListening(recognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				Utils.toast("听写失败,错误码：" + ret);
			} else {
				Utils.toast(mContext.getString(R.string.text_begin));
			}
		}
	}

	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			String result = SpeechJsonParser.parseIatResult(results
					.getResultString());
			notifyMsg(result);
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {

			Utils.toast(error.getPlainDescription(true));
		}

	};

	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			Utils.toast("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			Utils.toast(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			Utils.toast("结束说话");
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, String msg) {

		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = SpeechJsonParser.parseIatResult(results
					.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
			if (isLast) {
				// TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			Utils.toast("当前正在说话，音量大小：" + volume);
		}

	};
	public static final int MSG_RESPONSE = 0;

	/**
	 * 参数设置
	 * 
	 * @param param
	 * @return
	 */
	@SuppressLint("SdCardPath")
	public void setParam() {
		String lag = mSharedPreferences.getString("iat_language_preference",
				"mandarin");
		if (mIat == null) {
			// 初始化识别对象
			mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
		}
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}
		// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_BOS,
				mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		// 设置语音后端点
		mIat.setParameter(SpeechConstant.VAD_EOS,
				mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_PTT,
				mSharedPreferences.getString("iat_punc_preference", "1"));
		// 设置音频保存路径
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				"/sdcard/iflytek/wavaudio.pcm");
	}


	protected void notifyMsg(String msgInfo) {

		if(mMsgHandler != null){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("msg", msgInfo);
			msg.setData(bundle);
			msg.what = MSG_RESPONSE ;
			mMsgHandler.sendMessage(msg);
		}
	}

}
