package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.User
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class UserDataRepositoryTest {

    private lateinit var userDataRepository: UserDataRepository
    private lateinit var dataSource: UserDataSource

    @Before
    fun setUp() {
        dataSource = mock<UserDataSource>()
        userDataRepository = UserDataRepository(dataSource)
    }

    @Test
    fun findUser() {
        val login = "login"
        userDataRepository.findUser(login)

        verify(dataSource).find(login)
    }

    @Test
    fun saveUser() {
        val user = User()
        userDataRepository.saveUser(user)

        verify(dataSource).save(user)
    }

}