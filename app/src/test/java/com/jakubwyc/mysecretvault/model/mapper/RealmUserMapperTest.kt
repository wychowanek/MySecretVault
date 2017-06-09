package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.repository.RealmUser
import org.junit.Test

import org.junit.Assert.*

class RealmUserMapperTest {
    @Test
    fun map() {
        val mapper = RealmUserMapper()
        val realmUser = RealmUser()

        val user = mapper.map(realmUser)

        assertEquals(realmUser.login, user.login)
        assertEquals(realmUser.passwordHash, user.passwordHash)
    }
}