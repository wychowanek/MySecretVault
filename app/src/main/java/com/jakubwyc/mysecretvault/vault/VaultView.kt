package com.jakubwyc.mysecretvault.vault

import com.jakubwyc.mysecretvault.model.Treasure

interface VaultView {

    fun goToEditTreasureScreen(treasure: Treasure)

    fun showSnackbar(messageId: Int)

    fun renderTreasures(treasures: List<Treasure>)

}
