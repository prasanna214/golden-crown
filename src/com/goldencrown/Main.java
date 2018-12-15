package com.goldencrown;

import com.goldencrown.controller.TameOfThrones;
import com.goldencrown.model.Universe;
import com.goldencrown.view.ConsoleIO;
import com.goldencrown.view.IO;

import static com.goldencrown.controller.helpers.ActionFactory.actionMap;
import static com.goldencrown.controller.helpers.UniverseInitializer.initialize;

public class Main {
    public static void main(String[] args) {
        Universe universe = initialize();
        IO consoleIO = new ConsoleIO(System.out, System.in);
        TameOfThrones tameOfThrones = new TameOfThrones(universe, consoleIO, actionMap);
        tameOfThrones.start();
    }
}
