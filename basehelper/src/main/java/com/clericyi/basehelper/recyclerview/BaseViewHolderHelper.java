package com.clericyi.basehelper.recyclerview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * author: ClericYi
 * time: 2019-11-18 07:34
 **/
public class BaseViewHolderHelper extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray;
    private View viewHolder;

    private BaseViewHolderHelper(@NonNull View itemView) {
        super(itemView);
        this.viewHolder = itemView;
        viewArray = new SparseArray<>();
    }

    /**
     * 获取View，使用强制转化来调用方法
     *
     * @param itemId
     * @return
     * @throws NullPointerException
     */
    public View getView(@IdRes int itemId) throws NullPointerException {
        if (viewHolder != null) {
            View view = viewHolder.findViewById(itemId);
            viewArray.append(itemId, view);
            return view;
        }
        throw new NullPointerException("itemView为空");
    }

    /**
     * 已知View
     *
     * @param itemView
     * @return BaseViewHolderHelper
     */
    public static BaseViewHolderHelper build(View itemView) {
        return new BaseViewHolderHelper(itemView);
    }

    /**
     * 只知道layoutId的情况下
     *
     * @param context
     * @param layoutId
     * @param parent
     * @return BaseViewHolderHelper
     */
    public static BaseViewHolderHelper build(Context context, int layoutId, ViewGroup parent) {
        return new BaseViewHolderHelper(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }
}
