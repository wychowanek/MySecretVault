package com.jakubwyc.mysecretvault.view;

import com.jakubwyc.mysecretvault.model.Treasure;

public interface EditTreasureView {
    void goBackToVaultScreen();

    void renderTreasure(Treasure treasure);

    void hideKeyboard();

}
