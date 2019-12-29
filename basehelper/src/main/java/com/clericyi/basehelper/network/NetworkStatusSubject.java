package com.clericyi.basehelper.network;

/**
 * author: ClericYi
 * time: 2019-11-18
 */
public interface NetworkStatusSubject {
    /**
     * 添加观察者
     * @param observer 观察者
     */
    void addObserver(NetworkStatusObserver observer);

    /**
     * 删除观察者
     * @param observer 观察者
     */
    void removeObserver(NetworkStatusObserver observer);

    /**
     * 通知观察者改变状态
     * @param type 状态
     */
    void notifyObservers(boolean type);
}
