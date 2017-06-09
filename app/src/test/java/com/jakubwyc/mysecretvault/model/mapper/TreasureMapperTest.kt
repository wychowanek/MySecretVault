package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.model.Treasure
import org.junit.Assert.assertEquals
import org.junit.Test

class TreasureMapperTest {

    @Test
    fun map() {
        val mapper = TreasureMapper()
        val treasure = Treasure()

        val realmTreasure = mapper.map(treasure)

        assertEquals(treasure.text, realmTreasure.text)
        assertEquals(treasure.date, realmTreasure.date)
    }

}