package com.jakubwyc.mysecretvault.treasure

import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.repository.TreasureRepository
import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import rx.Observable
import java.util.*

class EditTreasurePresenterTest {

    private lateinit var systemContext: SystemContext
    private lateinit var treasureRepository: TreasureRepository
    private lateinit var editTreasureView: EditTreasureView
    private lateinit var editTreasurePresenter: EditTreasurePresenter

    @Before
    fun setUp() {
        systemContext = mock<SystemContext>()
        treasureRepository = mock<TreasureRepository>()
        editTreasureView = mock<EditTreasureView>()

        whenever(systemContext.treasureRepository).thenReturn(treasureRepository)
        whenever(treasureRepository.saveTreasure(any<Treasure>())).thenReturn(Observable.just(true))

        editTreasurePresenter = EditTreasurePresenter(systemContext)
    }

    @Test
    fun testShowTreasure() {
        editTreasurePresenter.attachView(editTreasureView)

        val treasure = Treasure()
        editTreasurePresenter.showTreasure(treasure)

        verify(editTreasureView).renderTreasure(treasure)
    }

    @Test
    fun testShowTreasureWithDetachedView() {
        editTreasurePresenter.attachView(editTreasureView)
        editTreasurePresenter.detachView()

        val treasure = Treasure()
        editTreasurePresenter.showTreasure(treasure)

        verify(editTreasureView, never()).renderTreasure(treasure)
    }

    @Test
    fun testSaveTreasureWithView() {
        editTreasurePresenter.attachView(editTreasureView)
        editTreasurePresenter.saveTreasure()

        verify(editTreasureView, times(1)).goBackToVaultScreen()
    }

    @Test
    fun testSaveTreasureWithoutView() {
        editTreasurePresenter.saveTreasure()

        verify(editTreasureView, never()).goBackToVaultScreen()
    }

    @Test
    fun testSaveTreasureWithDetachedView() {
        editTreasurePresenter.attachView(editTreasureView)
        editTreasurePresenter.detachView()
        editTreasurePresenter.saveTreasure()

        verify(editTreasureView, never()).goBackToVaultScreen()
    }

    @Test
    fun testSaveTreasureWithoutTextChangedWithoutEditedTreasure() {
        editTreasurePresenter.saveTreasure()

        argumentCaptor<Treasure>().apply {
            verify(treasureRepository).saveTreasure(capture())

            assertEquals("", firstValue.text)
            assertTrue(firstValue.date <= Date().time)
        }
    }

    @Test
    fun testSaveTreasureWithTextChangedWithoutEditedTreasure() {
        val treasureText = "text"
        editTreasurePresenter.treasureTextChanged(treasureText)
        editTreasurePresenter.saveTreasure()

        argumentCaptor<Treasure>().apply {
            verify(treasureRepository).saveTreasure(capture())

            assertEquals(treasureText, firstValue.text)
            assertTrue(firstValue.date <= Date().time)
        }
    }

    @Test
    fun testSaveTreasureWithoutTextChangedWithEditedTreasure() {
        val treasure = Treasure()
        editTreasurePresenter.showTreasure(treasure)
        editTreasurePresenter.saveTreasure()

        argumentCaptor<Treasure>().apply {
            verify(treasureRepository).saveTreasure(capture())

            assertEquals(treasure.text, firstValue.text)
            assertEquals(treasure.date, firstValue.date)
        }
    }

    @Test
    fun testSaveTreasureWithTextChangedWithEditedTreasure() {
        val treasure = Treasure()
        val treasureText = "text"
        editTreasurePresenter.showTreasure(treasure)
        editTreasurePresenter.treasureTextChanged(treasureText)
        editTreasurePresenter.saveTreasure()

        argumentCaptor<Treasure>().apply {
            verify(treasureRepository).saveTreasure(capture())

            assertEquals(treasureText, firstValue.text)
            assertEquals(treasure.date, firstValue.date)
        }
    }
}