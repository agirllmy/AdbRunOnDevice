package com.test.adb.adbtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by limengying on 2017/1/20.
 */

public class NormalDeviceControlActivity extends Activity {
    private static final int EVENT_COMMAND_RESULT = 100;
    private static final int EVENT_KILL_SERVER_RESULT = 101;
    private static final int EVENT_START_SERVER_RESULT = 102;

    private TCPClient mTCPClient;
    private Unbinder mUnbinder;

    @BindView(R.id.adb_kill_server)
    Button adb_kill_server;
    @BindView(R.id.adb_start_server)
    Button adb_start_server;
    @BindView(R.id.adb_devices)
    Button adb_devices;
    @BindView(R.id.input_ip)
    EditText input_ip;
    @BindView(R.id.adb_connect)
    Button adb_connect;
    @BindView(R.id.command_result)
    TextView command_result;
    @BindView(R.id.adb_disconnect)
    Button adb_disconnect;
    @BindView(R.id.adb_install)
    Button adb_install;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null || msg.obj == null) {
                return;
            }
            switch (msg.what) {
                case EVENT_COMMAND_RESULT:
                    command_result.setText((String) msg.obj);
                    break;
                case EVENT_START_SERVER_RESULT:
                    command_result.setText((Integer) msg.obj == 1 ? "成功启动" : "启动失败");
                    break;
                case EVENT_KILL_SERVER_RESULT:
                    command_result.setText((Integer) msg.obj == 1 ? "成功关闭" : "关闭失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_devices_control_view);
        mUnbinder = ButterKnife.bind(this);
        mTCPClient = TCPClient.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccessful = mTCPClient.startServer();
                Message msg = mHandler.obtainMessage(EVENT_START_SERVER_RESULT);
                msg.obj = isSuccessful ? 1 : 0;
                mHandler.sendMessage(msg);
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTCPClient.closeServer();
            }
        }).start();
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.adb_kill_server)
    public void adbKillServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccessful = mTCPClient.closeServer();
                Message msg = mHandler.obtainMessage(EVENT_KILL_SERVER_RESULT);
                msg.obj = isSuccessful ? 1 : 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @OnClick(R.id.adb_start_server)
    public void adbStartServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccessful = mTCPClient.startServer();
                Message msg = mHandler.obtainMessage(EVENT_START_SERVER_RESULT);
                msg.obj = isSuccessful ? 1 : 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @OnClick(R.id.adb_devices)
    public void adbDevice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String reslut = mTCPClient.sendInstructions(TCPClient.adb_devices);
                Message msg = mHandler.obtainMessage(EVENT_COMMAND_RESULT);
                msg.obj = reslut;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @OnClick(R.id.adb_connect)
    public void adbConnect() {
        String ip = input_ip.getEditableText().toString();
        if (!Utils.isIpAvailable(ip)) {
            command_result.setText("IP不合法");
            return;
        }
        String command = String.format(TCPClient.adb_connect, ip);
        String commandLengthHex = Utils.getHexString(command.length());
        if ("0000".equals(commandLengthHex)) {
            command_result.setText("命令不合法");
            return;
        }
        final String formatCommand = commandLengthHex + command;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String reslut = mTCPClient.sendInstructions(formatCommand);
                Message msg = mHandler.obtainMessage(EVENT_COMMAND_RESULT);
                msg.obj = reslut;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @OnClick(R.id.adb_disconnect)
    public void adbDisconnect() {
        Log.e("lmy","adb disconnect");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String reslut = mTCPClient.sendInstructions(TCPClient.adb_disconnect);
                Message msg = mHandler.obtainMessage(EVENT_COMMAND_RESULT);
                msg.obj = reslut;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @OnClick(R.id.adb_install)
    public void adbInstall() {

    }
}
