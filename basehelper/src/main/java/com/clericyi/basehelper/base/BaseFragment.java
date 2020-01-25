package com.clericyi.basehelper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.clericyi.basehelper.network.interfaces.NetworkStatusObserver;

/**
 * author: ClericYi
 * time: 2020-01-25
 * 需要思考的问题就是当前的Fragment属于哪个Activity
 */
public class BaseFragment extends Fragment implements NetworkStatusObserver {

    private short workStatus = WORK_NOT_OVER;

    //用于防止需要网络的线程任务重复运行
    private static final short WORK_OVER = 1;
    private static final short WORK_NOT_OVER = 2;

    @Override
    public void updateNetworkStatus(boolean type) {
        // 网络允许的状况下才能运行
        if(type && workStatus == WORK_NOT_OVER) {
            try {
                initWorks();
            }catch (Exception e){
                workStatus = WORK_NOT_OVER;
                return;
            }
            workStatus = WORK_OVER;
        }
    }

    /**
     * 这个函数中放置着线程任务
     */
    protected void initWorks() throws Exception{}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseActivity parent = (BaseActivity) getActivity();
        if(parent != null) parent.addObserver(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
