package com.clericyi.basehelper;

import android.app.Application;

import com.clericyi.basehelper.network.NetworkStatusObserver;
import com.clericyi.basehelper.network.NetworkStatusSubject;
import com.clericyi.basehelper.util.NetworkUtil;

import java.util.Stack;
import java.util.Vector;

/**
 * author: ClericYi
 * time: 2019-11-18
 */
public class App extends Application implements NetworkStatusSubject {

    private static App instance;
    private Vector<NetworkStatusObserver> observers = new Vector<>();
    private boolean networkCurrentStatus;
    private static Stack<BaseActivity> activities = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkCurrentStatus = NetworkUtil.isNetConnected(getBaseContext());
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
     * 关闭所有的Activity
     */
    public void finishActivities() {
        for (BaseActivity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 把Activity加入Stack
     *
     * @param activity 活动
     */
    public void addActivityStack(BaseActivity activity) {
        activities.add(activity);
    }

    /**
     * 关闭的Activity弹出
     */
    public void popActivityStack() {
        if (activities.size() > 0) {
            activities.pop();
        }
    }
}