package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.model.mapper.RealmTreasureMapper
import com.jakubwyc.mysecretvault.model.mapper.TreasureMapper
import com.vicpin.krealmextensions.allItemsAsObservable
import com.vicpin.krealmextensions.delete
import com.vicpin.krealmextensions.save
import rx.Observable

class RealmTreasureDataSource : TreasureDataSource {

    private val treasureMapper = TreasureMapper()
    private val realmTreasureMapper = RealmTreasureMapper()

    override fun save(treasure: Treasure): Observable<Boolean> {
        return Observable.create<Boolean> { subscriber ->
            val realmTreasure = treasureMapper.map(treasure)
            realmTreasure.save()
            subscriber.onNext(java.lang.Boolean.TRUE)
            subscriber.onCompleted()
        }
    }

    override fun remove(treasure: Treasure): Observable<Boolean> {
        return Observable.create<Boolean> { subscriber ->
            RealmTreasure().delete { it.equalTo("date", treasure.date) }
            subscriber.onNext(java.lang.Boolean.TRUE)
            subscriber.onCompleted()
        }
    }

    override fun removeAll(treasures: List<Treasure>): Observable<Boolean> {
        return Observable.create<Boolean> { subscriber ->
            treasures.map { it.date }.forEach { date ->
                RealmTreasure().delete { it.equalTo("date", date) }
            }
            subscriber.onNext(java.lang.Boolean.TRUE)
            subscriber.onCompleted()
        }
    }

    override fun findAll(): Observable<List<Treasure>> {
        return RealmTreasure().allItemsAsObservable().flatMap { list ->
            Observable.from(list).map<Treasure> { realmTreasureMapper.map(it) }.toList()
        }
    }
}