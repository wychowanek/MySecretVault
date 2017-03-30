package com.jakubwyc.mysecretvault;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jakewharton.rxbinding.view.RxView;
import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.presenter.VaultPresenter;
import com.jakubwyc.mysecretvault.repository.Repository;
import com.jakubwyc.mysecretvault.view.VaultView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VaultActivity extends AppCompatActivity implements VaultView, TreasureOnClickListener {

    public static final int NEW_TREASURE_REQUEST = 123;
    private VaultPresenter presenter;
    private Unbinder unbinder;
    @BindView(R.id.vault_parent_view)
    CoordinatorLayout parentView;
    @BindView(R.id.vault_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private TreasureAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        unbinder = ButterKnife.bind(this);
        setupRecyclerView();

        presenter = new VaultPresenter();
        presenter.attachView(this);
        presenter.onStart();

        RxView.clicks(fab).subscribe(presenter::onNewTreasureClicked);
    }

    private void setupRecyclerView() {
        recyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void goToEditTreasureScreen(final Treasure treasure) {
        Intent intent = new Intent(this, EditTreasureActivity.class);
        if (treasure != null) {
            intent.putExtra(getString(R.string.extra_treasure), treasure);
        }
        startActivityForResult(intent, NEW_TREASURE_REQUEST);
    }

    @Override
    public void showSnackbar(int messageId) {
        Snackbar.make(parentView, messageId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void renderTreasures(final List<Treasure> treasures) {
        adapter = new TreasureAdapter(treasures, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_TREASURE_REQUEST) {
            if (resultCode == EditTreasureActivity.RESULT_TREASURE_SAVED) {
                showSnackbar(R.string.treasure_saved);
            }
        }
    }

    @Override
    public void onTreasureClick(final Treasure treasure) {
        presenter.onEditTreasureClicked(treasure);
    }

    final ItemTouchHelper.SimpleCallback itemSwipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int adapterPosition = viewHolder.getAdapterPosition();
            final Treasure treasureSwiped = adapter.getTreasureAt(adapterPosition);
            presenter.addCandidateToRemove(treasureSwiped);
            adapter.removeTreasure(treasureSwiped, adapterPosition);

            Snackbar.make(parentView, R.string.treasure_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.toast_undo, v -> {
                        presenter.removeCandidateToRemove(treasureSwiped);
                        adapter.addTreasure(treasureSwiped, adapterPosition);
                    })
                    .setCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(final Snackbar snackbar, final int event) {
                            presenter.removeTreasures(event);
                        }
                    }).show();
        }
    };
}
