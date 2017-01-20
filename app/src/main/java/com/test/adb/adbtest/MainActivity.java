package com.test.adb.adbtest;

import android.app.Activity;
import android.os.Bundle;

import butterknife.Unbinder;

public class MainActivity extends Activity {
    private Unbinder unBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }
}
