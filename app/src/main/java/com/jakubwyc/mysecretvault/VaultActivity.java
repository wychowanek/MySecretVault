package com.jakubwyc.mysecretvault;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jakubwyc.mysecretvault.presenter.VaultPresenter;
import com.jakubwyc.mysecretvault.view.VaultView;

public class VaultActivity extends AppCompatActivity implements VaultView {

    private VaultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        presenter = new VaultPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

}
