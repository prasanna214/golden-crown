package com.tameofthrones.controller.actions;

import com.tameofthrones.model.Universe;
import com.tameofthrones.view.IO;

public class InvalidAction implements Action {

    private static final String INVALID_OPTION = "Kindly choose from available options";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        consoleIO.display(INVALID_OPTION);
    }
}
