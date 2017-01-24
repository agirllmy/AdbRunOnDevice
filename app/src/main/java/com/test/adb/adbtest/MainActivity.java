package com.test.adb.adbtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Unbinder unBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    @OnClick(R.id.alibaba_device_selector)
    public void onAlibabaDeviceSelected() {
        Log.e("lmy", "onAlibabaDeviceSelected");
        startActivity(new Intent(this, AlibabaDeviceControlActivity.class));
    }

    @OnClick(R.id.normal_tv_box_selector)
    public void onNorlmalDeviceSelected() {
        Log.e("lmy", "onNorlmalDeviceSelected");
        startActivity(new Intent(this, NormalDeviceControlActivity.class));
    }

}
