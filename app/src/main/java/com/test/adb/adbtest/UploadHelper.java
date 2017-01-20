package com.test.adb.adbtest;

import android.os.Environment;

import com.iqiyi.android.dlna.sdk.controlpoint.MediaType;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

/**
 * Created by limengying on 2017/1/20.
 */

public class UploadHelper  {
    private final OkHttpClient client = new OkHttpClient();
    File file new File(Environment.getExternalStorageDirectory(),"\"balabala.mp4\"");
    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
    RequestBody requestBody = new MultipartBuilder()
            .type(MultipartBuilder.FORM)
            .addPart(Headers.of(
                    "Content-Disposition",
                    "form-data; name=\"username\""),
                    RequestBody.create(null, "张鸿洋"))
            .addPart(Headers.of(
                    "Content-Disposition",
                    "form-data; name=\"mFile\";filename=\"wjd.mp4\""), fileBody)
            .build();

    Request request = new Request.Builder()
            .url(" http://192.168.199.158:7890/upload")
            .post(requestBody)
            .build();

    private void init(){
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }
}
