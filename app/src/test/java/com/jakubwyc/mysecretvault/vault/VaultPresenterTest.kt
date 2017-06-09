package com.jakubwyc.mysecretvault.vault

import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.repository.TreasureRepository
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import rx.Observable
import java.util.*

class VaultPresenterTest {

    private lateinit var vaultPresenter: VaultPresenter
    private lateinit var vaultView: VaultView
    private lateinit var systemContext: SystemContext
    private lateinit var treasureRepository: TreasureRepository
    private val treasureList = listOf(Treasure("text1", 234L), Treasure("text2", 345L))

    @Before
    fun setUp() {
        systemContext = mock<SystemContext>()
        treasureRepository = mock<TreasureRepository>()
        vaultView = mock<VaultView>()

        vaultPresenter = VaultPresenter(systemContext)

        whenever(systemContext.treasureRepository).thenReturn(treasureRepository)
        whenever(treasureRepository.removeAllTreasures(any())).thenReturn(Observable.just(true))
        whenever(treasureRepository.findAllTreasures()).thenReturn(Observable.just(treasureList))
    }

    @Test
    fun testOnNewTreasureClickedWithAttachedView() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.onNewTreasureClicked()

        verify(vaultView, times(1)).goToEditTreasureScreen(Treasure())
    }

    @Test
    fun testOnNewTreasureClickedWithDetachedView() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.detachView()
        vaultPresenter.onNewTreasureClicked()

        verify(vaultView, never()).goToEditTreasureScreen(com.nhaarman.mockito_kotlin.any())
    }

    @Test
    fun testOnEditTreasureClickedWithAttachedView() {
        vaultPresenter.attachView(vaultView)
        val treasure = Treasure("text", Date().time)
        vaultPresenter.onEditTreasureClicked(treasure)

        verify(vaultView, times(1)).goToEditTreasureScreen(treasure)
    }

    @Test
    fun testOnEditTreasureClickedWithDetachedView() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.detachView()
        val treasure = Treasure("text", Date().time)
        vaultPresenter.onEditTreasureClicked(treasure)

        verify(vaultView, never()).goToEditTreasureScreen(com.nhaarman.mockito_kotlin.any())
    }

    @Test
    fun testRemoveTreasuresWithNoTreasuresToBeRemoved() {
        vaultPresenter.removeTreasures()

        verify(treasureRepository, never()).removeAllTreasures(any())
    }

    @Test
    fun testRemoveTreasuresWithTreasuresToBeRemoved() {
        val treasureSwiped = Treasure("text", 123L)
        vaultPresenter.addCandidateToRemove(treasureSwiped)
        vaultPresenter.removeTreasures()

        verify(treasureRepository).removeAllTreasures(mutableListOf(treasureSwiped))

        vaultPresenter.removeTreasures()

        verify(treasureRepository, times(1)).removeAllTreasures(any())
    }

    @Test
    fun testRemoveTreasuresWithAddingAndRemovingTreasuresToBeRemoved() {
        val treasureSwiped = Treasure("text", 123L)
        vaultPresenter.addCandidateToRemove(treasureSwiped)
        vaultPresenter.removeCandidateToRemove(treasureSwiped)
        vaultPresenter.removeTreasures()

        verify(treasureRepository, never()).removeAllTreasures(any())
    }

    @Test
    fun testOnStart() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.onStart()

        verify(vaultView).renderTreasures(treasureList)
    }
}