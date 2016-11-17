package com.jakubwyc.mysecretvault.presenter;

import com.jakubwyc.mysecretvault.view.VaultView;

public class VaultPresenter implements Presenter<VaultView>{

    private VaultView view;

    @Override
    public void attachView(VaultView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
