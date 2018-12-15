package com.goldencrown.controller.actions;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class KnowRuler implements Action {

    private static final String RULER_NAME = "Ruler Name : ";
    private static final String ALLIES_OF_RULER = "Allies of Ruler : ";
    private static final String NONE = "None";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        if (isNull(universe) || isNull(consoleIO)) {
            return;
        }
        Kingdom ruler = universe.getRuler();
        if (isNull(ruler)) {
            displayRulerInfo(consoleIO, NONE, NONE);
            return;
        }
        displayRulerInfo(consoleIO, ruler.getName(), getAlliesOfRuler(ruler));
    }

    private void displayRulerInfo(IO consoleIO, String name, String allies) {
        consoleIO.display(RULER_NAME + name);
        consoleIO.display(ALLIES_OF_RULER + allies);
    }

    private String getAlliesOfRuler(Kingdom ruler) {
        if (ruler.getAllies().isEmpty()) {
            return NONE;
        }
        return ruler.getAllies().stream().map(Kingdom::getName)
                .collect(Collectors.joining(", "));
    }
}
