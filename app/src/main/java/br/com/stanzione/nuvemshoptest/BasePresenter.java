package br.com.stanzione.nuvemshoptest;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void dispose();
}

