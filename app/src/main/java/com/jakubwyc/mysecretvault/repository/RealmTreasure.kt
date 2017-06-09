package com.jakubwyc.mysecretvault.repository

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmTreasure : RealmObject() {

    @Required
    var text: String = ""
    @PrimaryKey
    var date: Long = 0

}
