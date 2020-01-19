package com.clericyi.basehelper.base;

import java.lang.ref.WeakReference;

/**
 * author: ClericYi
 * time: 2020-01-18
 */
public abstract class BasePresenter<V extends BaseActivity, M extends BaseModel, CONTRACT> {

    protected M m;
    // 使用弱引用，防止内存泄漏
    private WeakReference<V> vWeakReference;

    public BasePresenter() {
        m = getModel();
    }

    /**
     * 绑定View
     *
     * @param v View
     */
    protected void bindView(V v) {
        vWeakReference = new WeakReference<>(v);
    }

    /**
     * 解除绑定
     */
    protected void unBindView() {
        if (vWeakReference != null) {
            vWeakReference.clear();
            vWeakReference = null;
            System.gc();
        }
    }


    public V getView() {
        if (vWeakReference != null) {
            return vWeakReference.get();
        }
        return null;
    }

    public abstract CONTRACT getContract();

    protected abstract M getModel();
}
