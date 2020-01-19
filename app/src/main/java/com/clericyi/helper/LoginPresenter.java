package com.clericyi.helper;

import android.os.SystemClock;

import com.clericyi.basehelper.base.BasePresenter;

public class LoginPresenter extends BasePresenter<MainActivity, LoginMode, LoginContract.Presenter> {

    @Override
    public LoginContract.Presenter getContract() {
        // 既要履行View给它的需求，又要分配工作给Model去完成这个需求
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                try {
                    // 三种风格（P层很极端，要么不做事只做转发，要么就是拼命一个人干活）
                    m.getContract().executeLogin(name, pwd);
                    // 内存泄露测试
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(200000);
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                // 不管谁完成需求，有结果就告知View层
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

    @Override
    public LoginMode getModel() {
        return new LoginMode(this);
    }
}
