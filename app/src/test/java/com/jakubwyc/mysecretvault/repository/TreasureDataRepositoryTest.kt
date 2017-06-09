package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.Treasure
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class TreasureDataRepositoryTest {

    private lateinit var treasureDataRepository: TreasureDataRepository
    private lateinit var dataSource: TreasureDataSource

    @Before
    fun setUp() {
        dataSource = mock<TreasureDataSource>()
        treasureDataRepository = TreasureDataRepository(dataSource)
    }

    @Test
    fun saveTreasure() {
        val treasure = Treasure()
        treasureDataRepository.saveTreasure(treasure)

        verify(dataSource).save(treasure)
    }

    @Test
    fun removeTreasure() {
        val treasure = Treasure()
        treasureDataRepository.removeTreasure(treasure)

        verify(dataSource).remove(treasure)
    }

    @Test
    fun removeAllTreasures() {
        val treasures = listOf(Treasure(), Treasure())
        treasureDataRepository.removeAllTreasures(treasures)

        verify(dataSource).removeAll(treasures)
    }

    @Test
    fun findAllTreasures() {
        treasureDataRepository.findAllTreasures()

        verify(dataSource).findAll()
    }

}