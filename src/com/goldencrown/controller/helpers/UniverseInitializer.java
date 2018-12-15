package com.goldencrown.controller.helpers;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Universe;

import java.util.ArrayList;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;

public class UniverseInitializer {

    public static Universe initialize(){
        ArrayList<Kingdom> kingdoms = new ArrayList<>(kingdomMap.values());
        return new Universe(kingdoms);
    }
}
