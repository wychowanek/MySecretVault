package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.repository.RealmTreasure


class RealmTreasureMapper : Mapper<RealmTreasure, Treasure> {
    override fun map(data: RealmTreasure) = Treasure(data.text, data.date)
}
