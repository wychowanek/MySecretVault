package com.jakubwyc.mysecretvault.vault

import com.jakubwyc.mysecretvault.Presenter
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class VaultPresenter(private val systemContext: SystemContext) : Presenter<VaultView> {

    private var view: VaultView? = null
    private val treasuresToBeRemoved = mutableListOf<Treasure>()
    private val subscriptions = CompositeSubscription()
    private val treasureRepository
        get() = systemContext.treasureRepository

    override fun attachView(view: VaultView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun onStart() {
        subscriptions.add(
                treasureRepository.findAllTreasures()
                        .subscribe { treasures ->
                            view?.renderTreasures(treasures)
                        })
    }

    fun onNewTreasureClicked() {
        view?.goToEditTreasureScreen(Treasure())
    }

    fun onEditTreasureClicked(treasure: Treasure) {
        view?.goToEditTreasureScreen(treasure)
    }

    fun addCandidateToRemove(treasureSwiped: Treasure) {
        treasuresToBeRemoved.add(treasureSwiped)
    }

    fun removeCandidateToRemove(treasureSwiped: Treasure) {
        treasuresToBeRemoved.remove(treasureSwiped)
    }

    fun removeTreasures() {
        Timber.d("Treasures to be removed: $treasuresToBeRemoved")

        if (treasuresToBeRemoved.isNotEmpty())
            subscriptions.add(
                    treasureRepository.removeAllTreasures(treasuresToBeRemoved.toList())
                            .subscribe { treasuresToBeRemoved.clear() })
    }
}
