package com.jakubwyc.mysecretvault.presenter;


public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
