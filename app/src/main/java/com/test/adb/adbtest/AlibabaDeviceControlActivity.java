package com.test.adb.adbtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.adb.adbtest.http.UploadHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by limengying on 2017/1/20.
 */

public class AlibabaDeviceControlActivity extends Activity {
    public static final String TAG = AlibabaDeviceControlActivity.class.getSimpleName();
    Unbinder mUnbinder;
    @BindView(R.id.alibaba_device_ip) EditText mInputIpAddress;
    @BindView(R.id.upload_page) WebView mUploadWebPage;
    @BindView(R.id.upload_result) TextView mUploadResult;


    private static final int EVENT_UPLOAD_FILE_RESULT = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg == null) {
                return;
            }
            switch (msg.what) {
                case EVENT_UPLOAD_FILE_RESULT:
                    String result = (String) msg.obj;
                    mUploadResult.setText(result);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alibaba_devices_control_view);
        mUnbinder = ButterKnife.bind(this);
        mInputIpAddress.setText(Utils.getIpPrefix(this));
        mInputIpAddress.setSelection(mInputIpAddress.length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.alibaba_install)
    public void install() {
        final String ip = mInputIpAddress.getEditableText().toString();
        if (!Utils.isIpAvailable(ip)) {
            Toast.makeText(this, "输入合法IP~", Toast.LENGTH_SHORT).show();
        } else {
            mUploadWebPage.loadUrl(String.format("http://%s:7890",ip));
            // 上传文件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = UploadHelper.upload(ip);
                        Message msg = mHandler.obtainMessage(EVENT_UPLOAD_FILE_RESULT);
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        Log.e(TAG, "upload error!!!");
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
