package com.jakubwyc.mysecretvault.vault

import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.repository.TreasureRepository
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
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
        systemContext = mock(SystemContext::class.java)
        treasureRepository = mock(TreasureRepository::class.java)
        vaultView = mock(VaultView::class.java)

        vaultPresenter = VaultPresenter(systemContext)

        `when`(systemContext.treasureRepository).thenReturn(treasureRepository)
        `when`(treasureRepository.removeAllTreasures(ArgumentMatchers.anyList())).thenReturn(Observable.just(true))
        `when`(treasureRepository.findAllTreasures()).thenReturn(Observable.just(treasureList))
    }

    @Test
    fun testOnNewTreasureClickedWithAttachedView() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.onNewTreasureClicked()

        verify(vaultView, times(1)).goToEditTreasureScreen(null)
    }

    @Test
    fun testOnNewTreasureClickedWithDetachedView() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.detachView()
        vaultPresenter.onNewTreasureClicked()

        verify(vaultView, never()).goToEditTreasureScreen(ArgumentMatchers.any())
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

        verify(vaultView, never()).goToEditTreasureScreen(ArgumentMatchers.any())
    }

    @Test
    fun testRemoveTreasuresWithNoTreasuresToBeRemoved() {
        vaultPresenter.removeTreasures()

        verify(treasureRepository, never()).removeAllTreasures(ArgumentMatchers.anyList())
    }

    @Test
    fun testRemoveTreasuresWithTreasuresToBeRemoved() {
        val treasureSwiped = Treasure("text", 123L)
        vaultPresenter.addCandidateToRemove(treasureSwiped)
        vaultPresenter.removeTreasures()

        verify(treasureRepository).removeAllTreasures(mutableListOf(treasureSwiped))

        vaultPresenter.removeTreasures()

        verify(treasureRepository, times(1)).removeAllTreasures(anyList())
    }

    @Test
    fun testRemoveTreasuresWithAddingAndRemovingTreasuresToBeRemoved() {
        val treasureSwiped = Treasure("text", 123L)
        vaultPresenter.addCandidateToRemove(treasureSwiped)
        vaultPresenter.removeCandidateToRemove(treasureSwiped)
        vaultPresenter.removeTreasures()

        verify(treasureRepository, never()).removeAllTreasures(ArgumentMatchers.anyList())
    }

    @Test
    fun testOnStart() {
        vaultPresenter.attachView(vaultView)
        vaultPresenter.onStart()

        verify(vaultView).renderTreasures(treasureList)
    }
}