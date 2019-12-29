package com.clericyi.basehelper.network;

/**
 * author: ClericYi
 * time: 2019-11-18
 */
public interface NetworkStatusObserver {

    /**
     * 观察者更新状态
     * @param type 状态：false为无连接；true为有连接
     */
    void updateNetworkStatus(boolean type);
}
