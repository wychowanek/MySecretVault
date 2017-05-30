package com.jakubwyc.mysecretvault.model

import com.jakubwyc.mysecretvault.repository.RealmUser

class User(val login: String = "", val passwordHash: ByteArray = byteArrayOf()) {
    companion object {
        fun of(realmUser: RealmUser?): User? {
            return when (realmUser) {
                null -> User()
                else -> User(realmUser.login, realmUser.passwordHash)
            }
        }
    }
}
