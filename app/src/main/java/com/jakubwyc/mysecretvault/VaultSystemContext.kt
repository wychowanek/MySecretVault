package com.jakubwyc.mysecretvault

import android.app.Application
import com.jakubwyc.mysecretvault.repository.*
import io.realm.Realm
import timber.log.Timber

class VaultSystemContext : Application(), SystemContext {

    override val treasureRepository: TreasureRepository
        get() = TreasureDataRepository(RealmTreasureDataSource())
    override val userRepository: UserRepository
        get() = UserDataRepository(RealmUserDataSource())

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Timber.plant(Timber.DebugTree())
    }
}

