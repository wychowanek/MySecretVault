package com.jakubwyc.mysecretvault.vault.create

interface CreateVaultView {

    fun showLoginEmptyToast()
    fun showPasswordEmptyToast()
    fun showPasswordRepeatEmptyToast()
    fun showPasswordsNotEqualToast()
    fun showUserSavedToast()
    fun goToMainScreen()

}
