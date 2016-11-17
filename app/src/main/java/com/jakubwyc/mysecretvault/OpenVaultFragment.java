package com.jakubwyc.mysecretvault;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jakubwyc.mysecretvault.presenter.OpenVaultPresenter;
import com.jakubwyc.mysecretvault.view.OpenVaultView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OpenVaultFragment extends Fragment implements OpenVaultView {

    private OpenVaultPresenter presenter;
    private OpenVaultFragmentListener listener;
    private Unbinder unbinder;

    @BindView(R.id.login_edit)
    EditText loginView;
    @BindView(R.id.password_edit)
    EditText passwordView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_vault, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        presenter = new OpenVaultPresenter();
        presenter.attachView(this);
        if (context instanceof OpenVaultFragmentListener) {
            listener = (OpenVaultFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OpenVaultFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.create_vault_button)
    @Override
    public void showCreateVaultScreen() {
        if (listener != null) {
            listener.onFragmentChange(MainActivity.VaultScreen.CREATE);
        }
    }

    @OnClick(R.id.open_vault_button)
    public void tryToOpenVault() {
        presenter.verifyUserCredentials(loginView.getText().toString(), passwordView.getText().toString());
    }

    @Override
    public void showVaultScreen() {
        if (listener != null) {
            listener.onFragmentChange(MainActivity.VaultScreen.OPEN);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public interface OpenVaultFragmentListener {
        void onFragmentChange(MainActivity.VaultScreen screen);
    }
}
