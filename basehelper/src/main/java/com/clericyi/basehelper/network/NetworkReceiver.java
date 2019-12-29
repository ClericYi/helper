package com.clericyi.basehelper.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.clericyi.basehelper.App;

import static com.clericyi.basehelper.util.NetworkUtil.isNetConnected;

/**
 * author: ClericYi
 * time: 2019-11-19
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) && App.getInstance() != null) {
            App.getInstance().notifyObservers(isNetConnected(context));
        }
    }
}
