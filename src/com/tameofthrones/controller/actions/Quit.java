package com.tameofthrones.controller.actions;

import com.tameofthrones.model.Universe;
import com.tameofthrones.view.IO;

public class Quit implements Action {

    private static final String THANK_YOU = "Thank you for your time";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        consoleIO.display(THANK_YOU);
    }
}
