package com.jakubwyc.mysecretvault.model

import java.util.*

class User(val login: String = "", val passwordHash: ByteArray = byteArrayOf()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as User

        if (login != other.login) return false
        if (!Arrays.equals(passwordHash, other.passwordHash)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + Arrays.hashCode(passwordHash)
        return result
    }
}