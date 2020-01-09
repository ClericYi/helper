package com.clericyi.basehelper.recyclerview;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.clericyi.basehelper.R;
import com.clericyi.basehelper.recyclerview.interfaces.LoadMoreListener;

import java.util.List;

/**
 * author: ClericYi
 * time: 2019-11-18 07:34
 */
public abstract class BaseAdapterHelper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = this.getClass().getName();

    private final int VIEW_EMPTY = 10000001;
    private final int VIEW_DATA = 10000002;
    private final int VIEW_LOAD_MORE = 10000003;

    private int showSize = 16;//用于控制条目显示的基数

    private LoadMoreListener mLoadMoreListener;


    protected List<T> mDatas;
    protected Context mContext;


    private int loadMoreView;//加载更多View
    private boolean hasLoadMore = false;
    private boolean isLoading = false;
    private int loadOverView;//没有更多的数据View
    private int emptyView;//无数据View


    public void setLoadMoreView(@LayoutRes int loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void setEmptyView(@LayoutRes int emptyView) {
        this.emptyView = emptyView;
    }

    public void setLoadOverView(int loadOverView) {
        this.loadOverView = loadOverView;
    }

    public void setShowSize(int showSize) {
        this.showSize = showSize;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    /**
     * @param context 上下文
     * @param datas   需要的数据集
     */
    public BaseAdapterHelper(Context context, List<T> datas) {
        mDatas = datas;
        mContext = context;
        init();
    }

    @NonNull
    @Override
    public BaseViewHolderHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_LOAD_MORE:
                return BaseViewHolderHelper.build(mContext, loadMoreView, parent);
            case VIEW_EMPTY:
                return BaseViewHolderHelper.build(mContext, emptyView, parent);
            case VIEW_DATA:
                return BaseViewHolderHelper.build(mContext, getLayoutId(), parent);
        }
        return BaseViewHolderHelper.build(mContext, getLayoutId(), parent);

    }

    /**
     * GridLayoutManager模式时， HeaderView、FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isLoadMoreView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }

    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        //数据小于显示基数不加载更多
        if (mDatas.size() < showSize) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() && !isLoading) {
                        scrollLoadMore();
                    }
                }
            }
        });
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {
        if (!isLoading) {
            if (mLoadMoreListener != null) {
                isLoading = true;
                mLoadMoreListener.loading();
            }
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return -1;
    }


    /**
     * 用于判定是LoadMore的位置
     *
     * @param position
     * @return
     */
    private boolean isLoadMoreView(int position) {
        return getItemCount() - 1 == position && hasLoadMore;
    }


    /**
     * 计算Item总数，包含footer
     *
     * @return
     */
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size() + hasLoadMore();
        }
        return 0;
    }

    /**
     * 区分布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int dataSize = getItemCount();
        if (dataSize == 0) {
            return VIEW_EMPTY;
        } else {
            if (isLoadMoreView(position)) {
                return VIEW_LOAD_MORE;
            } else {
                return VIEW_DATA;
            }
        }
    }

    /**
     * 用于判定是否应该显示Footer
     * 只要设置了Footer自动可以加载
     *
     * @return 是否含有Footer
     */
    private int hasLoadMore() {
        if (hasLoadMore) {
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

    /**
     * 适配器中需要初始化的部分
     */
    private void init() {
        loadMoreView = R.layout.recyclerview_defalut_loadmore;
        loadOverView = R.layout.recyclerview_defalut_loadover;
        emptyView = R.layout.recyclerview_defalut_empty;
    }
}
