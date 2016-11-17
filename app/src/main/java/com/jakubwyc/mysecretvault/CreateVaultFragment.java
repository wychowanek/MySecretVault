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

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakubwyc.mysecretvault.presenter.CreateVaultPresenter;
import com.jakubwyc.mysecretvault.view.CreateVaultView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateVaultFragment extends Fragment implements CreateVaultView {

    private Unbinder unbinder;
    private CreateVaultPresenter presenter;

    @BindView(R.id.create_login_edit)
    EditText loginView;

    @BindView(R.id.create_password_edit)
    EditText passwordView;

    @BindView(R.id.create_password_repeat_edit)
    EditText passwordRepeatView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_vault, container, false);
        unbinder = ButterKnife.bind(this, view);

        RxTextView.textChanges(loginView).subscribe(presenter::onLoginChanged);
        RxTextView.textChanges(passwordView).subscribe(presenter::onPasswordChanged);
        RxTextView.textChanges(passwordRepeatView).subscribe(presenter::onPasswordRepeatChanged);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new CreateVaultPresenter();
        presenter.attachView(this);
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

    @OnClick(R.id.create_create_vault_button)
    public void createVault() {
        presenter.createVault();
    }

    @Override
    public void showToast(int messageId) {
        Toast.makeText(getActivity(), messageId, Toast.LENGTH_SHORT).show();
    }
}
