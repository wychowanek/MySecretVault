package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.User
import rx.Observable

class UserDataRepository(private val dataSource: UserDataSource) : UserRepository {

    override fun findUser(login: String): Observable<User> = dataSource.find(login)

    override fun saveUser(user: User): Observable<Boolean> = dataSource.save(user)

}

