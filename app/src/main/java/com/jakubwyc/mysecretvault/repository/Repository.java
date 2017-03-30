package com.jakubwyc.mysecretvault.repository;


import android.content.Context;

import com.google.common.base.Optional;
import com.jakubwyc.mysecretvault.mapper.RealmTreasureMapper;
import com.jakubwyc.mysecretvault.mapper.TreasureMapper;
import com.jakubwyc.mysecretvault.mapper.UserMapper;
import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

public class Repository {

    private static Repository instance;
    private final UserMapper userMapper;
    private final TreasureMapper treasureMapper;
    private final RealmTreasureMapper realmTreasureMapper;

    private Repository() {
        this.userMapper = new UserMapper();
        this.treasureMapper = new TreasureMapper();
        this.realmTreasureMapper = new RealmTreasureMapper();
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
            try (Realm realmInstance = Realm.getDefaultInstance()) {
                realmInstance.executeTransaction(realm -> realm.insertOrUpdate(userMapper.map(user)));
                subscriber.onNext(Boolean.TRUE);
                subscriber.onCompleted();
            }
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

    public Observable<Boolean> saveTreasure(final Treasure treasure) {
        return Observable.create(subscriber -> {
            try (Realm realmInstance = Realm.getDefaultInstance()) {
                realmInstance.executeTransaction(realm -> realm.insertOrUpdate(treasureMapper.map(treasure)));
                subscriber.onNext(Boolean.TRUE);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<Treasure>> findTreasures() {
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmTreasure> treasures = realm.where(RealmTreasure.class).findAll();
        return treasures.asObservable().flatMap(list ->
                Observable.from(list).map(realmTreasureMapper::map).toList()
        );
    }

    public Observable<Boolean> removeTreasure(final Treasure treasure) {
        return Observable.create(subscriber -> {
            try (Realm realmInstance = Realm.getDefaultInstance()) {
                realmInstance.executeTransaction(realm -> {
                            final RealmTreasure realmTreasure = realm.where(RealmTreasure.class)
                                    .equalTo("date", treasure.getDate()).findFirst();
                            if (realmTreasure != null) {
                                realmTreasure.deleteFromRealm();
                            }
                        }
                );
                subscriber.onNext(Boolean.TRUE);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> removeTreasures(final List<Treasure> treasures) {
        return Observable.create(subscriber -> {
            try (Realm realmInstance = Realm.getDefaultInstance()) {
                realmInstance.executeTransaction(realm -> {
                            for (Treasure treasure : treasures) {
                                final RealmTreasure realmTreasure = realm.where(RealmTreasure.class)
                                        .equalTo("date", treasure.getDate()).findFirst();
                                if (realmTreasure != null) {
                                    realmTreasure.deleteFromRealm();
                                }
                            }
                        }
                );
                subscriber.onNext(Boolean.TRUE);
                subscriber.onCompleted();
            }
        });
    }

}
