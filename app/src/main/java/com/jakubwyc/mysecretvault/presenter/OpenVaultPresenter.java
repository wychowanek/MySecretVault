package com.jakubwyc.mysecretvault.presenter;

import com.jakubwyc.mysecretvault.model.User;
import com.jakubwyc.mysecretvault.repository.Repository;
import com.jakubwyc.mysecretvault.view.OpenVaultView;

import java.util.Arrays;

import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static com.jakubwyc.mysecretvault.Utils.sha256;

public class OpenVaultPresenter implements Presenter<OpenVaultView> {

    private OpenVaultView view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public void attachView(final OpenVaultView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        subscriptions.clear();
        view = null;
    }

    public void verifyUserCredentials(String login, String password) {
        subscriptions.add(Repository.getInstance().findUser(login)
                .subscribe(user -> {
                            boolean passwordCorrect = verifyPassword(user.or(User::nullUser), password);
                            if (passwordCorrect) {
                                view.showVaultScreen();
                            } else {
                                view.showMessage("User or password incorrect");
                            }
                        }
                ));
    }

    private boolean verifyPassword(User user, String password) {
        boolean passwordsEqual = Arrays.equals(sha256(password), user.getPasswordHash());
        Timber.i("Passwords equal: %s", passwordsEqual);
        return passwordsEqual;
    }
}
