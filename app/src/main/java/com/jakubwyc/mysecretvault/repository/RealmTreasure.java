package com.jakubwyc.mysecretvault.repository;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmTreasure extends RealmObject {

    @Required
    public String text;
    @PrimaryKey
    public long date;

}
