package com.jakubwyc.mysecretvault.repository;


import android.content.Context;

import com.google.common.base.Optional;
import com.jakubwyc.mysecretvault.model.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

public class Repository {

    private static Repository instance;

    private Repository() {
    }

    public static void initialize(final Context context) {
        if (instance == null) {
            RealmConfiguration config = new RealmConfiguration.Builder(context.getApplicationContext())
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            instance = new Repository();
        }
    }

    public static synchronized Repository getInstance() {
        if (instance == null) {
            throw new RuntimeException("Please first initialize repository");
        }
        return instance;
    }

    public Observable<Boolean> saveUser(final User user) {
        return Observable.create(subscriber -> {
            final Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(RealmUser.of(user));
            realm.commitTransaction();
            subscriber.onNext(Boolean.TRUE);
            subscriber.onCompleted();
            realm.close();
        });
    }

    public Observable<Optional<User>> findUser(final String login) {
        return Observable.create(subscriber -> {
            final RealmUser realmUser = Realm
                    .getDefaultInstance()
                    .where(RealmUser.class)
                    .equalTo("login", login)
                    .findFirst();
            subscriber.onNext(Optional.fromNullable(User.of(realmUser)));
            subscriber.onCompleted();
        });
    }


}
