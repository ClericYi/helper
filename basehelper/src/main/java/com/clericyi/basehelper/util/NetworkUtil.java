package com.clericyi.basehelper.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * author: ClericYi
 * time: 2019-11-18
 * 网络类
 */

public class NetworkUtil {
    /**
     * 检测网络是否连接
     * @param context 上下文
     * @return 结果
     */
    public static boolean isNetConnected(Context context) {
        boolean net = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                net = (networkInfo != null && networkInfo.isConnectedOrConnecting());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return net;
    }


}
