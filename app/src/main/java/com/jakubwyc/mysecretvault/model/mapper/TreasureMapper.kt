package com.jakubwyc.mysecretvault.model.mapper


import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.repository.RealmTreasure

class TreasureMapper : Mapper<Treasure, RealmTreasure> {

    override fun map(data: Treasure): RealmTreasure {
        val realmTreasure = RealmTreasure()
        realmTreasure.text = data.text
        realmTreasure.date = data.date
        return realmTreasure
    }
}
