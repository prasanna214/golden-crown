package com.tameofthrones;

import com.tameofthrones.controller.TameOfThrones;
import com.tameofthrones.model.Universe;
import com.tameofthrones.view.ConsoleIO;
import com.tameofthrones.view.IO;

import static com.tameofthrones.controller.helpers.ActionFactory.actionMap;
import static com.tameofthrones.controller.helpers.UniverseInitializer.initialize;

public class Main {
    public static void main(String[] args) {
        Universe universe = initialize();
        IO consoleIO = new ConsoleIO(System.out, System.in);
        TameOfThrones tameOfThrones = new TameOfThrones(universe, consoleIO, actionMap);
        tameOfThrones.start();
    }
}
