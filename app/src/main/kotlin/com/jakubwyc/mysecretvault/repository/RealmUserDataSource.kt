package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.User
import com.jakubwyc.mysecretvault.model.mapper.UserMapper
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import rx.Observable

class RealmUserDataSource : UserDataSource {

    private val userMapper = UserMapper()

    override fun save(user: User): Observable<Boolean> {
        return Observable.create<Boolean> { subscriber ->
            val realmUser = userMapper.map(user)
            realmUser.save()
            subscriber.onNext(java.lang.Boolean.TRUE)
            subscriber.onCompleted()
        }
    }

    override fun find(login: String): Observable<User> {
        return Observable.create<User> { subscriber ->
            val realmUser = RealmUser().queryFirst { it.equalTo("login", login) }
            subscriber.onNext(User.of(realmUser))
            subscriber.onCompleted()
        }
    }

}