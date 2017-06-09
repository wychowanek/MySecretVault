package com.jakubwyc.mysecretvault.vault.open

import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.repository.UserRepository
import com.jakubwyc.mysecretvault.sha256
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import rx.Observable

class OpenVaultPresenterTest {

    private lateinit var systemContext: SystemContext
    private lateinit var userRepository: UserRepository
    private lateinit var openVaultView: OpenVaultView
    private lateinit var openVaultPresenter: OpenVaultPresenter
    private val testUser = User("login", sha256("password"))
    private val testUser1 = User()

    @Before
    fun setUp() {
        systemContext = mock<SystemContext>()
        userRepository = mock<UserRepository>()
        openVaultView = mock<OpenVaultView>()

        whenever(systemContext.userRepository).thenReturn(userRepository)
        whenever(userRepository.findUser("login")).thenReturn(Observable.just(testUser))
        whenever(userRepository.findUser("login1")).thenReturn(Observable.just(testUser1))

        openVaultPresenter = OpenVaultPresenter(systemContext)
    }

    @Test
    fun testVerifyValidUserCredentials() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.verifyUserCredentials("login", "password")

        verify(openVaultView).showVaultScreen()
    }

    @Test
    fun testVerifyUserCredentialsWrongPassword() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.verifyUserCredentials("login", "wrong.password")

        verify(openVaultView).showMessage(any<String>())
    }

    @Test
    fun testVerifyUserCredentialsWrongLogin() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.verifyUserCredentials("login1", "password")

        verify(openVaultView).showMessage(any<String>())
    }

    @Test
    fun testVerifyValidUserCredentialsDetachedView() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.detachView()
        openVaultPresenter.verifyUserCredentials("login", "password")

        verify(openVaultView, never()).showVaultScreen()
        verify(openVaultView, never()).showMessage(any<String>())
    }

    @Test
    fun testVerifyUserCredentialsWrongPasswordDetachedView() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.detachView()
        openVaultPresenter.verifyUserCredentials("login", "wrong.password")

        verify(openVaultView, never()).showVaultScreen()
        verify(openVaultView, never()).showMessage(any<String>())    }

    @Test
    fun testVerifyUserCredentialsWrongLoginDetachedView() {
        openVaultPresenter.attachView(openVaultView)
        openVaultPresenter.detachView()
        openVaultPresenter.verifyUserCredentials("login1", "password")

        verify(openVaultView, never()).showVaultScreen()
        verify(openVaultView, never()).showMessage(any<String>())    }

}