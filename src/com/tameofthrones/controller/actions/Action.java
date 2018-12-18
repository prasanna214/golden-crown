package com.tameofthrones.controller.actions;

import com.tameofthrones.model.Universe;
import com.tameofthrones.view.IO;

public interface Action {
    void execute(Universe universe, IO consoleIO);
}
