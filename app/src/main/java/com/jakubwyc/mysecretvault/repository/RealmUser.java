package com.jakubwyc.mysecretvault.repository;

import com.jakubwyc.mysecretvault.model.User;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmUser extends RealmObject {

    @PrimaryKey
    public String login;

    @Required
    public byte[] passwordHash;

    public static RealmUser of(User user) {
        RealmUser realmUser = new RealmUser();
        realmUser.login = user.getLogin();
        realmUser.passwordHash = user.getPasswordHash();
        return realmUser;
    }

}
