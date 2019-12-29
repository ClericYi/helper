package com.clericyi.helper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.clericyi.basehelper.recyclerview.BaseAdapterHelper;
import com.clericyi.basehelper.recyclerview.BaseViewHolderHelper;

import java.util.List;

/**
 * author: ClericYi
 * time: 2019-12-04
 */
public class Adapter extends BaseAdapterHelper {

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolderHelper holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    /**
     * @param context 上下文
     * @param datas   需要的数据集
     */
    public Adapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_table;
    }

}
