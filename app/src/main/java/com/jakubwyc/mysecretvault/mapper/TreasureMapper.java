package com.jakubwyc.mysecretvault.mapper;


import com.jakubwyc.mysecretvault.model.Treasure;
import com.jakubwyc.mysecretvault.repository.RealmTreasure;

public class TreasureMapper implements Mapper<Treasure, RealmTreasure> {

    @Override
    public RealmTreasure map(Treasure data) {
        RealmTreasure realmTreasure = new RealmTreasure();
        realmTreasure.text = data.getText();
        realmTreasure.date = data.getDate();
        return realmTreasure;
    }
}
