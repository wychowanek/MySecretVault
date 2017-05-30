package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.Treasure
import rx.Observable

class TreasureDataRepository(private val dataSource: TreasureDataSource) : TreasureRepository {

    override fun saveTreasure(treasure: Treasure): Observable<Boolean> = dataSource.save(treasure)

    override fun removeTreasure(treasure: Treasure): Observable<Boolean> = dataSource.remove(treasure)

    override fun removeAllTreasures(treasures: List<Treasure>): Observable<Boolean> = dataSource.removeAll(treasures)

    override fun findAllTreasures(): Observable<List<Treasure>> = dataSource.findAll()

}

