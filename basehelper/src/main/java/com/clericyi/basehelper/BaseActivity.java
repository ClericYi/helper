package com.clericyi.basehelper;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.clericyi.basehelper.network.NetworkReceiver;
import com.clericyi.basehelper.network.NetworkStatusObserver;

import java.util.concurrent.TimeUnit;

/**
 * author: ClericYi
 * time: 2019-11-18
 */
public class BaseActivity extends AppCompatActivity implements NetworkStatusObserver {

    public final String TAG = this.getClass().getName();
    protected final String IS_FIRST_LAUNCH = "isFirstLaunch";
    private final TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;

    private NetworkReceiver networkReceiver;


    @Override
    public void updateNetworkStatus(boolean type) {

    }

    /**
     * Activity创建时加入观察者
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        App.getInstance().addActivityStack(this);
    }

    /**
     * 初始化网络连接广播
     */
    private void initNetwork() {
        App.getInstance().addObserver(this);
        networkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver, intentFilter);
    }

    /**
     * 用于创建立即执行的任务
     * @param r
     */
    protected void addThread(Runnable r) {
        App.getInstance().addScheduledThread(r, 0, defaultTimeUnit);
    }

    /**
     * 用于创建定时任务
     * @param r
     * @param waitTime
     * @param timeUnit
     */
    protected void addScheduledThread(Runnable r, int waitTime, TimeUnit timeUnit) {
        App.getInstance().addScheduledThread(r, waitTime, timeUnit);
    }

    /**
     * Activity销毁时删去观察者
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeObserver(this);
        unregisterReceiver(networkReceiver);
        App.getInstance().popActivityStack();
    }

    /**
     * 关闭所有的Activity
     */
    protected void finishAllActivities() {
        App.getInstance().finishActivities();
    }
}
