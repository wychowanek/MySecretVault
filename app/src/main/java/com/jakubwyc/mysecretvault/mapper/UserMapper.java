package com.jakubwyc.mysecretvault.mapper;


import com.jakubwyc.mysecretvault.model.User;
import com.jakubwyc.mysecretvault.repository.RealmUser;

public class UserMapper implements Mapper<User, RealmUser> {


    @Override
    public RealmUser map(User data) {
        RealmUser realmUser = new RealmUser();
        realmUser.login = data.getLogin();
        realmUser.passwordHash = data.getPasswordHash();
        return realmUser;
    }

}
