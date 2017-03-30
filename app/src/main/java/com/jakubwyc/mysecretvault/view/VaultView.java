package com.jakubwyc.mysecretvault.view;

import com.jakubwyc.mysecretvault.model.Treasure;

import java.util.List;

public interface VaultView {

    void goToEditTreasureScreen(Treasure treasure);

    void showSnackbar(int messageId);

    void renderTreasures(List<Treasure> treasures);

}
