package com.jakubwyc.mysecretvault.presenter;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.repository.Repository;
import com.jakubwyc.mysecretvault.view.VaultView;

import java.util.ArrayList;
import java.util.List;

public class VaultPresenter implements Presenter<VaultView> {

    private static final String TAG = "VaultPresenter";
    private VaultView view;
    private List<Treasure> treasuresToBeRemoved = new ArrayList<>();


    @Override
    public void attachView(VaultView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    private boolean isViewAttached() {
        return view != null;
    }

    public void onStart() {
        Repository.getInstance().findTreasures()
                .subscribe(treasures -> {
                    if (isViewAttached()) {
                        view.renderTreasures(treasures);
                    }
                });
    }

    public void onNewTreasureClicked(Void aVoid) {
        view.goToEditTreasureScreen(null);
    }

    public void onEditTreasureClicked(final Treasure treasure) {
        view.goToEditTreasureScreen(treasure);
    }

    public void addCandidateToRemove(final Treasure treasureSwiped) {
        treasuresToBeRemoved.add(treasureSwiped);
    }

    public void removeCandidateToRemove(final Treasure treasureSwiped) {
        treasuresToBeRemoved.remove(treasureSwiped);
    }

    public void removeTreasures(int event) {
        Log.d(TAG, "removeTreasures() called with " + "event = [" + event + "]");
        if (!treasuresToBeRemoved.isEmpty()
                && (event == Snackbar.Callback.DISMISS_EVENT_MANUAL
                || event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT
                || event == Snackbar.Callback.DISMISS_EVENT_ACTION
                || event == Snackbar.Callback.DISMISS_EVENT_SWIPE)) {
            Repository.getInstance().removeTreasures(treasuresToBeRemoved)
                    .subscribe($ -> treasuresToBeRemoved.clear());
        }
    }
}
