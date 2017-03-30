package com.jakubwyc.mysecretvault.mapper;

import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.repository.RealmTreasure;


public class RealmTreasureMapper implements Mapper<RealmTreasure, Treasure> {

    @Override
    public Treasure map(RealmTreasure data) {
        return new Treasure(data.text, data.date);
    }
}
