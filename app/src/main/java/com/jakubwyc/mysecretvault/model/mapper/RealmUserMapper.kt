package com.jakubwyc.mysecretvault.model.mapper

import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.repository.RealmUser

class RealmUserMapper : Mapper<RealmUser, User> {
    override fun map(data: RealmUser) = User(data.login, data.passwordHash)
}
