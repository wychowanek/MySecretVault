package com.jakubwyc.mysecretvault.vault.create

import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.repository.UserRepository
import com.jakubwyc.mysecretvault.sha256
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import rx.Observable

class CreateVaultPresenterTest {

    private lateinit var systemContext: SystemContext
    private lateinit var userRepository: UserRepository
    private lateinit var createVaultView: CreateVaultView
    private lateinit var createVaultPresenter: CreateVaultPresenter

    @Before
    fun setUp() {
        systemContext = mock<SystemContext>()
        userRepository = mock<UserRepository>()
        createVaultView = mock<CreateVaultView>()

        whenever(systemContext.userRepository).thenReturn(userRepository)
        whenever(userRepository.saveUser(any<User>())).thenReturn(Observable.just(true))

        createVaultPresenter = CreateVaultPresenter(systemContext)
    }

    @Test
    fun testWrongLogin() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.createVault()

        verify(createVaultView).showLoginEmptyToast()
    }

    @Test
    fun testWrongPassword() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.onLoginChanged("login")
        createVaultPresenter.createVault()

        verify(createVaultView).showPasswordEmptyToast()
    }

    @Test
    fun testWrongSecondPassword() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.onLoginChanged("login")
        createVaultPresenter.onPasswordChanged("password")
        createVaultPresenter.createVault()

        verify(createVaultView).showPasswordRepeatEmptyToast()
    }

    @Test
    fun testPasswordsNotEqual() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.onLoginChanged("login")
        createVaultPresenter.onPasswordChanged("password")
        createVaultPresenter.onPasswordRepeatChanged("password2")
        createVaultPresenter.createVault()

        verify(createVaultView).showPasswordsNotEqualToast()
    }

    @Test
    fun testCorrectData() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.onLoginChanged("login")
        createVaultPresenter.onPasswordChanged("password")
        createVaultPresenter.onPasswordRepeatChanged("password")
        createVaultPresenter.createVault()

        verify(createVaultView, never()).showPasswordsNotEqualToast()
        verify(createVaultView, never()).showLoginEmptyToast()
        verify(createVaultView, never()).showPasswordRepeatEmptyToast()
        verify(createVaultView, never()).showPasswordEmptyToast()
        verify(createVaultView).showUserSavedToast()
        verify(createVaultView).goToMainScreen()
        verify(userRepository).saveUser(User("login", sha256("password")))
    }

    @Test
    fun testCorrectDataWithoutView() {
        createVaultPresenter.attachView(createVaultView)
        createVaultPresenter.onLoginChanged("login")
        createVaultPresenter.onPasswordChanged("password")
        createVaultPresenter.onPasswordRepeatChanged("password")
        createVaultPresenter.detachView()
        createVaultPresenter.createVault()

        verify(createVaultView, never()).showPasswordsNotEqualToast()
        verify(createVaultView, never()).showLoginEmptyToast()
        verify(createVaultView, never()).showPasswordRepeatEmptyToast()
        verify(createVaultView, never()).showPasswordEmptyToast()
        verify(createVaultView, never()).showUserSavedToast()
        verify(createVaultView, never()).goToMainScreen()
        verify(userRepository).saveUser(User("login", sha256("password")))
    }
}