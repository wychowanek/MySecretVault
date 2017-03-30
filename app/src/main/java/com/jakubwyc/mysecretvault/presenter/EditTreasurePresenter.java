package com.jakubwyc.mysecretvault.presenter;

import android.support.annotation.NonNull;

import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.repository.Repository;
import com.jakubwyc.mysecretvault.view.EditTreasureView;

import rx.subscriptions.CompositeSubscription;

public class EditTreasurePresenter implements Presenter<EditTreasureView> {

    private EditTreasureView view;
    private String treasureText;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Treasure editedTreasure;

    @Override
    public void attachView(EditTreasureView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        subscriptions.clear();
    }

    public void saveTreasure(final Void aVoid) {
        subscriptions.add(Repository.getInstance().saveTreasure(getTreasure())
                .subscribe($ -> view.goBackToVaultScreen()));
    }

    @NonNull
    private Treasure getTreasure() {
        return new Treasure(treasureText, editedTreasure != null ? editedTreasure.getDate() : System.currentTimeMillis());
    }

    public void treasureTextChanged(CharSequence newItemText) {
        treasureText = newItemText.toString();
    }

    public void showTreasure(final Treasure treasure) {
        editedTreasure = treasure;
        view.renderTreasure(treasure);
    }
}
