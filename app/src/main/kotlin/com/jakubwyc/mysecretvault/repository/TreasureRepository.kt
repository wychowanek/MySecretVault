package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.Treasure
import rx.Observable

interface TreasureRepository {
    fun saveTreasure(treasure: Treasure): Observable<Boolean>
    fun removeTreasure(treasure: Treasure): Observable<Boolean>
    fun removeAllTreasures(treasures: List<Treasure>): Observable<Boolean>
    fun findAllTreasures(): Observable<List<Treasure>>
}