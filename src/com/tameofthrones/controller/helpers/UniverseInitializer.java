package com.tameofthrones.controller.helpers;

import com.tameofthrones.model.Kingdom;
import com.tameofthrones.model.Universe;

import java.util.ArrayList;

import static com.tameofthrones.controller.helpers.KingdomFactory.kingdomMap;

public class UniverseInitializer {

    public static Universe initialize(){
        ArrayList<Kingdom> kingdoms = new ArrayList<>(kingdomMap.values());
        return new Universe(kingdoms);
    }
}
