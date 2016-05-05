package com.itheima.voicedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn //
		// 请勿在“=”与appid之间添加任务空字符或者转义符
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=562c44bb");
	}

	public void startListen(View view) {
		// 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
		SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
		// 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		// 3.开始听写
		mIat.startListening(mRecoListener);
	}

	// 听写监听器
	private RecognizerListener mRecoListener = new RecognizerListener() {

		// 听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
		// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
		// 关于解析Json的代码可参见Demo中JsonParser类；
		// isLast等于true时会话结束。
		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			System.out.println(results.getResultString());
			System.out.println("isLast:" + isLast);
		}

		// 开始录音
		@Override
		public void onBeginOfSpeech() {

		}

		// 结束录音
		@Override
		public void onEndOfSpeech() {

		}

		// 会话发生错误回调接口
		@Override
		public void onError(SpeechError arg0) {

		}

		// 扩展用接口
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

		}

		// volume音量值0~30，data音频数据
		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {

		}

	};

	public void startListenUI(View view) {
		// 1.创建RecognizerDialog对象
		RecognizerDialog mDialog = new RecognizerDialog(this, null);
		// 2.设置accent、language等参数
		mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

		// 3.设置回调接口
		mDialog.setListener(new RecognizerDialogListener() {

			@Override
			public void onResult(RecognizerResult results, boolean isLast) {
				System.out.println(results.getResultString());
				System.out.println("isLast:" + isLast);
			}

			@Override
			public void onError(SpeechError arg0) {

			}
		});

		// 4.显示dialog，接收语音输入
		mDialog.show();
	}

	// 语音合成
	public void startSpeak(View view) {
		// 1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
		SpeechSynthesizer mTts = SpeechSynthesizer
				.createSynthesizer(this, null);
		// 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
		// 设置发音人（更多在线发音人，用户可参见 附录12.2
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaomei"); // 设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
		// 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
		// 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
		// 仅支持保存为pcm和wav格式，如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

		// 3.开始合成
		mTts.startSpeaking("床前明月光, 地上鞋两双, 一对狗男女,其中就有你!", null);
	}
}
