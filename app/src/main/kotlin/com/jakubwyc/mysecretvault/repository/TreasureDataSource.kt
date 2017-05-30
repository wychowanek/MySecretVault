package com.jakubwyc.mysecretvault.repository

import com.jakubwyc.mysecretvault.model.Treasure
import rx.Observable

interface TreasureDataSource {
    fun save(treasure: Treasure): Observable<Boolean>
    fun remove(treasure: Treasure): Observable<Boolean>
    fun removeAll(treasures: List<Treasure>): Observable<Boolean>
    fun findAll(): Observable<List<Treasure>>
}