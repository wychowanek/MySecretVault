package com.jakubwyc.mysecretvault.model.mapper


import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.repository.RealmUser

class UserMapper : Mapper<User, RealmUser> {


    override fun map(data: User): RealmUser {
        val realmUser = RealmUser()
        realmUser.login = data.login
        realmUser.passwordHash = data.passwordHash
        return realmUser
    }

}
