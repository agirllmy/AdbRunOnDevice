package com.test.adb.adbtest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by limengying on 2017/1/20.
 */

public class Utils {
    public static boolean isIpAvailable(String ipAdd) {
        if (TextUtils.isEmpty(ipAdd)) {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAdd.matches(regex);
    }

    public static String getIpAddress(Context context) {
        context = context.getApplicationContext();
        int ipAdress = getWIFILocalIpAdress(context);
        return (ipAdress & 0xFF) + "." + ((ipAdress >> 8) & 0xFF) + "." + ((ipAdress >> 16) & 0xFF) + "."
                + (ipAdress >> 24 & 0xFF);
    }

    public static String getHexString(int decimal) {
        String hex = Integer.toHexString(decimal);
        if (TextUtils.isEmpty(hex)) {
            return "0000";
        }
        switch (hex.length()) {
            case 1:
                return "000" + hex;
            case 2:
                return "00" + hex;
            case 3:
                return "0" + hex;
            case 4:
                return hex.toUpperCase();
            default:
                return "0000";
        }
    }
    public static String getIpPrefix(Context context) {
        context = context.getApplicationContext();
        int ipAdress = getWIFILocalIpAdress(context);
        return (ipAdress & 0xFF) + "." + ((ipAdress >> 8) & 0xFF) + "." + ((ipAdress >> 16) & 0xFF) + ".";
    }

    public static int getWIFILocalIpAdress(Context mContext) {

        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getIpAddress();
    }
}
