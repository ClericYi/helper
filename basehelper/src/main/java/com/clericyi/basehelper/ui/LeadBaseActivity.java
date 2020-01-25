package com.clericyi.basehelper.ui;

import android.content.Intent;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.clericyi.basehelper.base.BaseActivity;
import com.clericyi.basehelper.base.BaseFragment;
import com.clericyi.basehelper.util.SharePreferencesUtil;

import java.util.List;

/**
 * author: ClericYi
 * time: 2020-01-14
 * 基于ViewPager实现可滚动引导页
 * 底部的小圆点通过RadioGroup实现
 */
public abstract class LeadBaseActivity extends BaseActivity {

    protected int drawableId;
    protected RadioGroup radioGroup;
    protected List<BaseFragment> fragments;


    /**
     * 添加引导页小圆点
     *
     * @param group
     * @param size  需要的数量
     */
    protected void addCirclePoint(RadioGroup group, int size) {
        for (int i = 0; i < size; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setButtonDrawable(drawableId);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            group.addView(radioButton);
        }
        // 只能通过滑动来进行
        group.setClickable(false);
    }

    /**
     * 需要进行实例化fragments、radioGroup
     *
     * @param viewPager
     */
    protected void viewPagerSetting(ViewPager viewPager) {
        if (fragments == null || radioGroup == null) {
            Log.e(TAG, "在使用之前需要进行实例化已经准备好的变量fragments、radioGroup");
        }
        viewPager.setOffscreenPageLimit(4);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

    abstract int setRadioButtonAppearance();
}
