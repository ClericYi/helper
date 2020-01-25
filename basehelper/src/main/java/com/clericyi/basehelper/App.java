package com.clericyi.basehelper;

import android.app.Application;

import com.clericyi.basehelper.network.interfaces.NetworkStatusObserver;
import com.clericyi.basehelper.network.interfaces.NetworkStatusSubject;
import com.clericyi.basehelper.util.NetworkUtil;

import java.util.Vector;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: ClericYi
 * time: 2019-11-18
 */
public class App extends Application implements NetworkStatusSubject {

    private static App instance;

    private final static int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();//默认的核心数量

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private Vector<NetworkStatusObserver> observers = new Vector<>();
    private boolean networkCurrentStatus;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkCurrentStatus = NetworkUtil.isNetConnected(getBaseContext());
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE);
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void addObserver(NetworkStatusObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(NetworkStatusObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(boolean type) {
        // 防止重复传递网络状态
        if (networkCurrentStatus == type) {
            return;
        }
        networkCurrentStatus = type;
        for (NetworkStatusObserver observer : observers) {
            observer.updateNetworkStatus(networkCurrentStatus);
        }
    }

    /**
     * @param r
     * @param scheduledTime
     * @param timeUnit
     */
    public void addScheduledThread(Runnable r, int scheduledTime, TimeUnit timeUnit) {
        scheduledThreadPoolExecutor.schedule(r, scheduledTime, timeUnit);
    }
}