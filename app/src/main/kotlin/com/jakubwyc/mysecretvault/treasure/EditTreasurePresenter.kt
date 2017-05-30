package com.jakubwyc.mysecretvault.treasure

import com.jakubwyc.mysecretvault.Presenter
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import rx.subscriptions.CompositeSubscription
import java.util.*

class EditTreasurePresenter(private val systemContext: SystemContext) : Presenter<EditTreasureView> {

    private var view: EditTreasureView? = null
    private var treasureText: String = ""
    private val subscriptions = CompositeSubscription()
    private var editedTreasure: Treasure? = null
    private val treasureRepository
        get() = systemContext.treasureRepository

    override fun attachView(view: EditTreasureView) {
        this.view = view
    }

    override fun detachView() {
        view = null
        subscriptions.clear()
    }

    fun saveTreasure() {
        subscriptions.add(treasureRepository.saveTreasure(treasure.copy())
                .subscribe { view?.goBackToVaultScreen() })
    }

    private val treasure: Treasure
        get() = Treasure(treasureText, editedTreasure?.date ?: Date().time)

    fun treasureTextChanged(newItemText: CharSequence) {
        treasureText = newItemText.toString()
    }

    fun showTreasure(treasure: Treasure) {
        editedTreasure = treasure
        view?.renderTreasure(treasure)
    }
}
