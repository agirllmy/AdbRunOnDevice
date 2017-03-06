package com.test.adb.adbtest;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public static final String TAG = TCPClient.class.getSimpleName();
    public static boolean sServerStartCloseTag = false;
    // 显示当前连接的设备
    public static final String adb_devices = "000Chost:devices";
    // 查看某设备的目录
    public static final String adb_shell_ls = "0008shell:ls";
    // 查看某设备的日志
    public static final String adb_logcat = "0027shell:adb -s 192.168.199.173:5555 shell:logcat";
    // 启动adb服务
    public static final String adb_start_service = "0013tcp:localhost:5037";
    // 查看adb版本
    public static final String adb_version = "000Chost:version";
    // 连接ip
    public static final String adb_connect = "host:connect:%s";
    // 断开通过ip连接的设备
    public static final String adb_disconnect = "0010host:disconnect:";

    private Socket mSocket;
    private static volatile TCPClient mInstance;

    public static TCPClient getInstance() {
        if (mInstance == null) {
            mInstance = new TCPClient();
        }
        return mInstance;
    }

    public String sendInstructions(String info) {
        Log.e("lmy", "command = " + info);
        String result = "命令: " + info + '\n' + "结果: ";
        if (mSocket == null || !startServer()) {
            return result + "失败: Socke启动失败";
        }

        try {
            // 得到socket读写流
            OutputStream outputStream = mSocket.getOutputStream();
            PrintWriter printWiter = new PrintWriter(outputStream);
            // 得到输入流
            InputStream inputStream = mSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            printWiter.write(info);
            printWiter.flush();
            // 接收服务器的相应
            String reply = null;
            while (!((reply = bufferedReader.readLine()) == null)) {
                result += reply;
            }
            // 关闭资源
            outputStream.close();
            printWiter.close();
            inputStream.close();
            bufferedReader.close();
            return result;
        } catch (UnknownHostException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            result += "UnknownHostException";
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            result += "IOException";
        }
        return result;

    }

    public boolean startServer() {
        try {
            if (!closeServer()) {
                Log.e(TAG, "状态清零错误");
            }
            // 建立客户端socket连接，指定服务器位置及端口
            mSocket = new Socket("localhost", 5038);
            Log.e(TAG, "成功建立连接");
            return true;
        } catch (ConnectException ex) {
            Log.e(TAG, "startServer()" + ex.toString());
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            Log.e(TAG, "startServer()" + ex.toString());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean closeServer() {
        try {
            if (mSocket != null) {
                mSocket.close();
                printServerCloseLog("mSocket");
            }
            return true;
        } catch (IOException ex) {
            Log.e(TAG, "closeServer()" + ex.toString());
            ex.printStackTrace();
            return false;
        }

    }

    public static void printServerCloseLog(String name) {
        if (sServerStartCloseTag) {
            Log.e(TAG, name + " 关闭成功");
        }
    }
}