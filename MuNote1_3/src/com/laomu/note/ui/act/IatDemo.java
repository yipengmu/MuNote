package com.laomu.note.ui.act;

import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonParser;
import com.iflytek.cloud.DataDownloader;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.cloud.util.ContactManager.ContactListener;
import com.iflytek.cloud.util.UserWords;
import com.laomu.note.R;
import com.laomu.note.common.speech.SpeechJsonParser;

public class IatDemo extends Activity implements OnClickListener{
	private static String TAG = "IatDemo";
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog iatDialog;
	// 听写结果内容
	private EditText mResultText;
	// 用户词表下载结果
	private String mDownloadResult = "";

	private Toast mToast;

	private SharedPreferences mSharedPreferences;
	
	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.iatdemo);
		initLayout();

		// 初始化识别对象
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(this,mInitListener);
		
		mSharedPreferences = getSharedPreferences("xx", Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
		mResultText = ((EditText)findViewById(R.id.iat_text));
	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout(){
		findViewById(R.id.iat_recognize).setOnClickListener(this);
		findViewById(R.id.iat_recognize).setEnabled(false);
		findViewById(R.id.iat_upload_contacts).setOnClickListener(this);
		findViewById(R.id.iat_upload_userwords).setOnClickListener(this);	
		findViewById(R.id.iat_download_userwords).setOnClickListener(this);	
		findViewById(R.id.iat_stop).setOnClickListener(this);
		findViewById(R.id.iat_cancel).setOnClickListener(this);
		findViewById(R.id.image_iat_set).setOnClickListener(this);
	}

	int ret = 0;// 函数调用返回值
	@Override
	public void onClick(View view) {				
		switch (view.getId()) {
		// 进入参数设置页面
		case R.id.image_iat_set:
//			Intent intents = new Intent(IatDemo.this, IatSettings.class);
//			startActivity(intents);
			break;
			// 开始听写
		case R.id.iat_recognize:
			mResultText.setText(null);// 清空显示内容
			// 设置参数
			setParam();
			boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), true);
			if (isShowDialog) {
				// 显示听写对话框
				iatDialog.setListener(recognizerDialogListener);
				iatDialog.show();
				showTip(getString(R.string.text_begin));
			} else {
				// 不显示听写对话框
				ret = mIat.startListening(recognizerListener);
				if(ret != ErrorCode.SUCCESS){
					showTip("听写失败,错误码：" + ret);
				}else {
					showTip(getString(R.string.text_begin));
				}
			}
			break;
			// 停止听写
		case R.id.iat_stop: 
			mIat.stopListening();
			showTip("停止听写");
			break;
			// 取消听写
		case R.id.iat_cancel: 
			mIat.cancel();
			showTip("取消听写");
			break;
			// 上传联系人
		case R.id.iat_upload_contacts:
			Toast.makeText(IatDemo.this, getString(R.string.text_upload_contacts),Toast.LENGTH_SHORT).show();
			ContactManager mgr = ContactManager.createManager(IatDemo.this, mContactListener);	
			mgr.asyncQueryAllContactsName();
			break;
			// 上传用户词表
		case R.id.iat_upload_userwords:
			Toast.makeText(IatDemo.this, getString(R.string.text_upload_userwords),Toast.LENGTH_SHORT).show();
//			String contents = FucUtil.readFile(this, "userwords","utf-8");
			//指定引擎类型
			mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			mIat.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");			
//			ret = mIat.updateLexicon("userword", contents, lexiconListener);
			if(ret != ErrorCode.SUCCESS)
				showTip("上传热词失败,错误码：" + ret);
			break;
			//  下载用户词表
		case R.id.iat_download_userwords:
			mResultText.setText(null);// 清空显示内容
			// 创建数据下载实例
			DataDownloader dataDownloader = new DataDownloader(this);
			dataDownloader.setParameter(SpeechConstant.SUBJECT, "spp");
			dataDownloader.setParameter(SpeechConstant.DATA_TYPE, "userword");
			dataDownloader.downloadData(downloadlistener);
			break;
		default:
			break;
		}
	}


	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code == ErrorCode.SUCCESS) {
				findViewById(R.id.iat_recognize).setEnabled(true);
			}
		}
	};

	/**
	 * 用户词表下载监听器。
	 */
	private SpeechListener downloadlistener = new SpeechListener(){

		@Override
		public void onData(byte[] data) {
			try {
				if(data != null && data.length > 1)
					mDownloadResult = new String(data,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onCompleted(SpeechError error) {
			if(error != null){
				showTip(error.toString());
			}
			else if(TextUtils.isEmpty(mDownloadResult)){
				showTip(getString(R.string.text_userword_empty));
			}
			else{
				mResultText.setText("");
				UserWords userwords = new UserWords(mDownloadResult.toString());
				if(userwords == null || userwords.getKeys() == null)
				{
					showTip(getString(R.string.text_userword_empty));
					return ;
				}
				for(String key : userwords.getKeys())
				{
					mResultText.append(key + ":");
					for(String word : userwords.getWords(key))
					{
						mResultText.append(word + ",");
					}
					mResultText.append("\n");
				}
				showTip(getString(R.string.text_download_success));
			}
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
		}

	};
	
	/**
	 * 上传联系人/词表监听器。
	 */
	private LexiconListener lexiconListener = new LexiconListener() {

		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if(error != null){
				showTip(error.toString());
			}
			else{
				showTip(getString(R.string.text_upload_success));
			}
		}
	};
	
	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
			showTip("开始说话");
		}


		@Override
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			showTip("结束说话");
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, String msg) {

		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {		
			String text = SpeechJsonParser.parseIatResult(results.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
			if(isLast) {
				//TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			showTip("当前正在说话，音量大小：" + volume);
		}

	};

	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener(){
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = SpeechJsonParser.parseIatResult(results.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};

	/**
	 * 获取联系人监听器。
	 */
	private ContactListener mContactListener = new ContactListener() {
		@Override
		public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
			//注：实际应用中除第一次上传之外，之后应该通过changeFlag判断是否需要上传，否则会造成不必要的流量.
			//			if(changeFlag) {
			//指定引擎类型
			mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			mIat.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");			
			ret = mIat.updateLexicon("contact", contactInfos, lexiconListener);
			if(ret != ErrorCode.SUCCESS)
				showTip("上传联系人失败：" + ret);
			//			}		
		}		

	};

	private void showTip(final String str)
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}

	/**
	 * 参数设置
	 * @param param
	 * @return 
	 */
	@SuppressLint("SdCardPath")
	public void setParam(){
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		}else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT,lag);
		}
		// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		// 设置语音后端点
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		// 设置音频保存路径
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, "/sdcard/iflytek/wavaudio.pcm");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出时释放连接
		mIat.cancel();
		mIat.destroy();
	}
	
	@Override
	protected void onResume() {
		SpeechUtility.getUtility().checkServiceInstalled();
		super.onResume();
	}
}
