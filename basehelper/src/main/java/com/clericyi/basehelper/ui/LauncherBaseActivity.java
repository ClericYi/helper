package com.clericyi.basehelper.ui;

import android.content.Intent;
import android.os.Handler;

import com.clericyi.basehelper.BaseActivity;
import com.clericyi.basehelper.util.SharePreferencesUtil;


/**
 * author: ClericYi
 * time: 2020-01-14
 * 启动页
 */
public class LauncherBaseActivity extends BaseActivity {

    /**
     * 调用该函数用于自动判定跳转至主页面还是引导页
     *
     * @param mainActivity 主页面
     * @param leadActivity 引导页
     * @param waitTime     等待时间
     */
    protected void launchNewActivity(final BaseActivity mainActivity, final BaseActivity leadActivity, int waitTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isFirstLaunch()) {
                    intent = new Intent(LauncherBaseActivity.this, leadActivity.getClass());
                } else {
                    intent = new Intent(LauncherBaseActivity.this, mainActivity.getClass());
                }
                startActivity(intent);
                finish();
            }
        }, waitTime);

    }

    /**
     * 启动页中用于判定是不是第一次启动
     *
     * @return true/false
     */
    private boolean isFirstLaunch() {
        return SharePreferencesUtil.getBoolean(this, IS_FIRST_LAUNCH, false);
    }
}
