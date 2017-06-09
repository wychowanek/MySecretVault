package com.jakubwyc.mysecretvault.vault.open

import com.jakubwyc.mysecretvault.Presenter
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.sha256
import rx.subscriptions.CompositeSubscription
import java.util.*

class OpenVaultPresenter(private val systemContext: SystemContext) : Presenter<OpenVaultView> {

    private var view: OpenVaultView? = null
    private val subscriptions = CompositeSubscription()
    private val userRepository
        get() = systemContext.userRepository

    override fun attachView(view: OpenVaultView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        view = null
    }

    fun verifyUserCredentials(login: String, password: String) {
        subscriptions.add(userRepository.findUser(login)
                .subscribe { user ->
                    val passwordCorrect = verifyPassword(user, password)
                    if (passwordCorrect) {
                        view?.showVaultScreen()
                    } else {
                        view?.showMessage("User or password incorrect")
                    }
                })
    }

    private fun verifyPassword(user: User, password: String): Boolean {
        return Arrays.equals(sha256(password), user.passwordHash)
    }
}
