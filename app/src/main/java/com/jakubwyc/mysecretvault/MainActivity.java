package com.jakubwyc.mysecretvault;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jakubwyc.mysecretvault.presenter.MainPresenter;
import com.jakubwyc.mysecretvault.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, OpenVaultFragment.OpenVaultFragmentListener {

    public enum VaultScreen {
        OPEN, CREATE
    }

    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        presenter = new MainPresenter();
        presenter.attachView(this);
        showOpenVaultScreen();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showCreateVaultScreen() {
        replaceFragment(new CreateVaultFragment(), true);
    }

    @Override
    public void showOpenVaultScreen() {
        replaceFragment(new OpenVaultFragment(), false);
    }

    @Override
    public void showVaultScreen() {
        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(final Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentChange(final VaultScreen vaultScreen) {
        switch (vaultScreen) {
            case CREATE:
                showCreateVaultScreen();
                return;
            case OPEN:
                showVaultScreen();
        }
    }
}
