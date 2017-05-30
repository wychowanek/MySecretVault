package com.jakubwyc.mysecretvault.treasure

import com.jakubwyc.mysecretvault.model.Treasure

interface TreasureOnClickListener {
    fun onTreasureClick(treasure: Treasure)
}
