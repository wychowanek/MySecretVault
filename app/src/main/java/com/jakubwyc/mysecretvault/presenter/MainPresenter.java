package com.jakubwyc.mysecretvault.presenter;

import com.jakubwyc.mysecretvault.presenter.Presenter;
import com.jakubwyc.mysecretvault.view.MainView;

public class MainPresenter implements Presenter<MainView> {

    private MainView view;

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
