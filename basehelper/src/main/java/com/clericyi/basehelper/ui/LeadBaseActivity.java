package com.clericyi.basehelper.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.clericyi.basehelper.base.BaseActivity;
import com.clericyi.basehelper.util.DensityUtil;
import com.clericyi.basehelper.util.SharePreferencesUtil;

/**
 * author: ClericYi
 * time: 2020-01-14
 * 基于ViewPager实现可滚动引导页
 */
public abstract class LeadBaseActivity extends BaseActivity {

    protected int CIRCLE_POINT_SIZE = 16;


    /**
     * 添加引导页小圆点
     *
     * @param viewGroup
     * @param size      需要的数量
     */
    protected void addCirclePoint(ViewGroup viewGroup, int size) {
        for (int i = 0; i < size; i++) {
            RadioButton view = new RadioButton(this);
            setRaidBtnAttribute(view, i);
            viewGroup.addView(view);
        }
    }

    private void setRaidBtnAttribute(RadioButton codeBtn, int id) {
        codeBtn.setButtonDrawable(new ColorDrawable(Color.CYAN));
        codeBtn.setId(id);
        codeBtn.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DensityUtil.dp2px(this, CIRCLE_POINT_SIZE));
        codeBtn.setLayoutParams(rlp);
    }

    /**
     * 改变启动的状态，第二次在访问时不会再访问到引导页
     */
    private void changeLaunchSetting() {
        SharePreferencesUtil.putBoolean(this, IS_FIRST_LAUNCH, true);
    }

    /**
     * 启动主页面一定要调用这个方法
     *
     * @param mainActivity
     */
    protected void startMainActivity(BaseActivity mainActivity) {
        changeLaunchSetting();
        startActivity(new Intent(LeadBaseActivity.this, mainActivity.getClass()));
        finish();
    }
}
