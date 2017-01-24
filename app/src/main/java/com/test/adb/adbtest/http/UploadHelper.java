package com.test.adb.adbtest.http;

import android.os.Environment;
import android.util.Log;


import com.squareup.okhttp.MediaType;
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

    public static String upload(String ip) throws IOException{
        String result = null;
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/octet-stream");
        File file =  new File("/sdcard/tvassistant-release.apk");
        if(!file.exists()) {
            result = "file not exist!!!";
        }
        String url = String.format("http://%s:7890/upload",ip);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,file))
                .build();
        Response response = client.newCall(request).execute();
        result = response == null ? null:response.body().string();
        return result;

    }
}
