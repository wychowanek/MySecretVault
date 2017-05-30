package com.jakubwyc.mysecretvault.treasure

import com.jakubwyc.mysecretvault.model.Treasure

interface EditTreasureView {
    fun goBackToVaultScreen()

    fun renderTreasure(treasure: Treasure)

    fun hideKeyboard()

}
