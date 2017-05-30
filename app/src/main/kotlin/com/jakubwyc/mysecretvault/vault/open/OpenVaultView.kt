package com.jakubwyc.mysecretvault.vault.open

interface OpenVaultView {

    fun showCreateVaultScreen()
    fun showVaultScreen()
    fun showMessage(message: String)
    fun clearUserData()
}
