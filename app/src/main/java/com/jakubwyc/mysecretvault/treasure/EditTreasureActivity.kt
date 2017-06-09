package com.jakubwyc.mysecretvault.treasure

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import kotlinx.android.synthetic.main.activity_new_treasure.*
import rx.subscriptions.CompositeSubscription

const val RESULT_TREASURE_SAVED = 1

class EditTreasureActivity : AppCompatActivity(), EditTreasureView {

    private lateinit var presenter: EditTreasurePresenter
    private val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_treasure)

        presenter = EditTreasurePresenter(applicationContext as SystemContext)
        presenter.attachView(this)

        val treasure = intent.getParcelableExtra<Parcelable>(getString(R.string.extra_treasure)) as? Treasure
        if (treasure != null) {
            presenter.showTreasure(treasure)
        }

        subscriptions.add(RxView.clicks(saveButton).subscribe { presenter.saveTreasure() })
        subscriptions.add(RxTextView.textChanges(treasureText).subscribe { presenter.treasureTextChanged(it) })
    }

    override fun onDestroy() {
        presenter.detachView()
        subscriptions.clear()
        super.onDestroy()
    }

    override fun goBackToVaultScreen() {
        hideKeyboard()
        setResult(RESULT_TREASURE_SAVED)
        finish()
    }

    override fun renderTreasure(treasure: Treasure) {
        treasureText.setText(treasure.text)
        treasureText.setSelection(treasureText.length())
        showKeyboard()
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(treasureText.windowToken, 0)
    }
}
