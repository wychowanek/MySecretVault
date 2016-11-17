package com.jakubwyc.mysecretvault.model;

import com.jakubwyc.mysecretvault.repository.RealmUser;

public class User {

    private String login;
    private byte[] passwordHash;

    public User(String login, byte[] passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public static User nullUser() {
        return new User("", new byte[]{});
    }

    public static User of(RealmUser realmUser) {
        return realmUser == null ? null : new User(realmUser.login, realmUser.passwordHash);
    }
}
