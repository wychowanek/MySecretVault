package com.jakubwyc.mysecretvault.repository

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmUser : RealmObject() {

    @PrimaryKey
    var login = ""

    @Required
    var passwordHash: ByteArray = byteArrayOf()

}
