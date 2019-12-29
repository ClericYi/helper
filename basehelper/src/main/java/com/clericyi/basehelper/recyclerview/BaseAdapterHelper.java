package com.clericyi.basehelper.recyclerview;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * author: ClericYi
 * time: 2019-11-18 07:34
 */
public abstract class BaseAdapterHelper extends RecyclerView.Adapter<BaseViewHolderHelper> {

    protected final String TAG = this.getClass().getName();

    protected List mDatas;
    protected Context mContext;
    private int footerView;
    private boolean hasFooter = false;


    public void setFooterView(int footerView) {
        this.footerView = footerView;
        hasFooter = true;
    }

    /**
     * @param context 上下文
     * @param datas   需要的数据集
     */
    public BaseAdapterHelper(Context context, List datas) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolderHelper holder, int position) {
        if (hasFooter) {
            Log.e(TAG, "hasFooter");
            return;
        }
    }

    @NonNull
    @Override
    public BaseViewHolderHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseViewHolderHelper.build(mContext, getLayoutId(), parent);
    }


    /**
     * 计算Item总数，包含footer
     * @return
     */
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size() + hasFooter();
        }
        return 0;
    }

    /**
     * 用于判定是否应该显示Footer
     * 只要设置了Footer自动可以加载
     *
     * @return 是否含有Footer
     */
    private int hasFooter() {
        if (hasFooter) {
            return 1;
        }
        return 0;
    }

    /**
     * 填写对应的布局Id
     *
     * @return 布局Id
     */
    protected abstract int getLayoutId();
}
