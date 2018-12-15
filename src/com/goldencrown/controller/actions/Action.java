package com.goldencrown.controller.actions;

import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

public interface Action {
    void execute(Universe universe, IO consoleIO);
}
