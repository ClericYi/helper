package com.clericyi.basehelper.base;

/**
 * author: ClericYi
 * time: 2020-01-18
 */
public abstract class BaseModel<P extends BasePresenter, CONTRACT> {

    protected P p;

    public BaseModel(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}