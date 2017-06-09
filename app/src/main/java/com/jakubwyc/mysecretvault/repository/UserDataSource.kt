package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.User
import rx.Observable

interface UserDataSource {
    fun save(user: User): Observable<Boolean>
    fun find(login: String): Observable<User>
}