package com.example.wifilocation997.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.example.wifilocation997.entity.Coordinate;
import com.example.wifilocation997.entity.FingerPrint;
import com.google.gson.Gson;

import java.util.List;

public class GetWifi{
    private final String TAG = "Y30J";
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1000;
    private final int UPDATE_UI_REQUEST_CODE = 1024;
    private final String BASE0 = "happyhome";
    private final String BASE1 = "Pokemmo";
    private final String BASE2 = "CMCC-MC5A";
    private final String BASE3 = "midea_fa_2503";
    private final String BASE4 = "ChinaNet-eq7k";

    //这里是访问地址
    private final String baseUrl = "https://h5556095v9.zicp.fun";

private Context context;
private Activity activity;

    private FingerPrint fingerPrint;
    private StringBuffer mScanResultStr;    // 暂存WiFi扫描结果的字符串
    private WifiManager mWifiManager;   // 调用WiFi各种API的对象
    private String result;

    public GetWifi(Context context){
        this.context=context;
        activity = (Activity)context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        getLocationAccessPermission();  // 先获取位置权限
        //scanWifi();
    }

    /**
     * 增加开启位置权限功能，以适应Android 6.0及以上的版本
     */
    private void getLocationAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
    }

    public Coordinate scanWifi() {
        // 如果WiFi未打开，先打开WiFi
        if (!mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(true);

        // 开始扫描WiFi
        mWifiManager.startScan();
        // 获取并保存WiFi扫描结果
        Log.d(TAG, "scanWifi: ");
        mScanResultStr = new StringBuffer();
        fingerPrint = new FingerPrint(0, 0, -100, -100, -100, -100, -100);
        List<ScanResult> scanResults = mWifiManager.getScanResults();
        for (ScanResult sr : scanResults) {
            switch (sr.SSID) {
                case BASE0:
                    fingerPrint.setSs1(sr.level);
                    break;
                case BASE1:
                    fingerPrint.setSs2(sr.level);
                    break;
                case BASE2:
                    fingerPrint.setSs3(sr.level);
                    break;
                case BASE3:
                    fingerPrint.setSs4(sr.level);
                    break;
                case BASE4:
                    fingerPrint.setSs5(sr.level);
                    break;
            }
            mScanResultStr.append("SSID: ").append(sr.SSID).append("\n");
            mScanResultStr.append("MAC Address: ").append(sr.BSSID).append("\n");
            mScanResultStr.append("Signal Strength(dBm): ").append(sr.level).append("\n\n");
            Log.d(TAG, "SSID:" + sr.SSID + "  MAC Address: " + sr.BSSID + "  Signal Strength:" + sr.level);
        }

        Log.d(TAG, "RESULT: " + fingerPrint.getSs1() + "  " + fingerPrint.getSs2() + "  " + fingerPrint.getSs3() + "  " + fingerPrint.getSs4() + "  " + fingerPrint.getSs5());
        return upload();
    }

    private Coordinate upload() {

        fingerPrint.setPositionX(0);
        fingerPrint.setPositionY(0);
        Coordinate resObj = null;

        //将封装好的对象发送给服务器
        Gson gson = new Gson();
        String json = gson.toJson(fingerPrint);
        String args[] = new String[]{"location", "location"};
        try {
            result = OKHttpUtil.postSyncRequest(baseUrl, json, args);
            Log.d("服务器返回值:", result);
            resObj = gson.fromJson(result,Coordinate.class);
            //Log.d("Finger", String.valueOf(res.getPositionX())+String.valueOf(res.getPositionY()));
        } catch (Exception e) {
            //连接失败
            //Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
        return resObj;
    }

}