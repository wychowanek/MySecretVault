package com.jakubwyc.mysecretvault.presenter;

import android.text.TextUtils;

import com.jakubwyc.mysecretvault.R;
import com.jakubwyc.mysecretvault.model.User;
import com.jakubwyc.mysecretvault.repository.Repository;
import com.jakubwyc.mysecretvault.view.CreateVaultView;

import rx.subscriptions.CompositeSubscription;

import static com.jakubwyc.mysecretvault.Utils.sha256;

public class CreateVaultPresenter implements Presenter<CreateVaultView> {

    private CreateVaultView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    private String login;
    private String password;
    private String passwordRepeat;

    @Override
    public void attachView(CreateVaultView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        subscriptions.clear();
        view = null;
    }

    public void onLoginChanged(final CharSequence value) {
        login = value.toString();
    }

    public void onPasswordChanged(final CharSequence value) {
        password = value.toString();
    }

    public void onPasswordRepeatChanged(final CharSequence value) {
        passwordRepeat = value.toString();
    }

    public void createVault() {
        if (isUserDataValid()) {
            User user = new User(login, sha256(password));
            saveUser(user);
        }
    }

    private boolean isUserDataValid() {
        boolean valid = false;
        if (TextUtils.isEmpty(login)) {
            view.showToast(R.string.login_empty);
        } else if (TextUtils.isEmpty(password)) {
            view.showToast(R.string.password_empty);
        } else if (TextUtils.isEmpty(passwordRepeat)) {
            view.showToast(R.string.password_repeat_empty);
        } else if (!TextUtils.equals(password, passwordRepeat)) {
            view.showToast(R.string.passwords_not_equal);
        } else {
            valid = true;
        }
        return valid;
    }

    private void saveUser(User user) {
        subscriptions.add(Repository.getInstance().saveUser(user)
                .subscribe($ -> view.showToast(R.string.user_saved))
        );
    }
}
