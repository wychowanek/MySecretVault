package com.jakubwyc.mysecretvault;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.presenter.EditTreasurePresenter;
import com.jakubwyc.mysecretvault.view.EditTreasureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

public class EditTreasureActivity extends AppCompatActivity implements EditTreasureView {

    public static final int RESULT_TREASURE_SAVED = 1;

    private EditTreasurePresenter presenter;
    @BindView(R.id.save)
    Button saveButton;
    @BindView(R.id.new_item)
    EditText treasureText;

    private Unbinder unbinder;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treasure);

        unbinder = ButterKnife.bind(this);

        presenter = new EditTreasurePresenter();
        presenter.attachView(this);

        final Parcelable treasure = getIntent().getParcelableExtra(getString(R.string.extra_treasure));
        if (treasure != null) {
            presenter.showTreasure((Treasure) treasure);
        }

        subscriptions.add(RxView.clicks(saveButton).subscribe(presenter::saveTreasure));
        subscriptions.add(RxTextView.textChanges(treasureText).subscribe(presenter::treasureTextChanged));
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        unbinder.unbind();
        subscriptions.clear();
        super.onDestroy();
    }

    @Override
    public void goBackToVaultScreen() {
        hideKeyboard();
        setResult(RESULT_TREASURE_SAVED);
        finish();
    }

    @Override
    public void renderTreasure(final Treasure treasure) {
        treasureText.setText(treasure.getText());
        treasureText.setSelection(treasureText.length());
        showKeyboard();
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(treasureText.getWindowToken(), 0);
    }
}
