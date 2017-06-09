package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {
    @Test
    fun map() {
        val mapper = UserMapper()
        val user = User()

        val realmUser = mapper.map(user)

        assertEquals(user.login, realmUser.login)
        assertEquals(user.passwordHash, realmUser.passwordHash)
    }

}