package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.repository.RealmTreasure
import org.junit.Test

import org.junit.Assert.*

class RealmTreasureMapperTest {

    @Test
    fun map() {
        val mapper = RealmTreasureMapper()
        val realmTreasure = RealmTreasure()

        val treasure = mapper.map(realmTreasure)

        assertEquals(realmTreasure.text, treasure.text)
        assertEquals(realmTreasure.date, treasure.date)
    }

}