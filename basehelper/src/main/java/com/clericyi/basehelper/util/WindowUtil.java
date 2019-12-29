package com.clericyi.basehelper.util;

import android.content.Context;

/**
 * author: ClericYi
 * time: 2019-11-18 07:34
 **/
public class WindowUtil {

    /**
     * 获取Window的屏幕高度
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context){
         return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取Window的屏幕宽度
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
