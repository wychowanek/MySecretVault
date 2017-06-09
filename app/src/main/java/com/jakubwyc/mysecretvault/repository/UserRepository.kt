package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.User
import rx.Observable

interface UserRepository {
    fun findUser(login: String): Observable<User>
    fun saveUser(user: User): Observable<Boolean>
}