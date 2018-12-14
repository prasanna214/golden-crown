package com.goldencrown.controller;

import com.goldencrown.model.Universe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniverseInitializerTest {

    @Test
    void shouldInitializeUniverseWithKingdomsFromKingdomMap() {
        Universe universe = UniverseInitializer.initialize();

        assertTrue(Constants.kingdomMap.values().containsAll(universe.getKingdoms()));
    }
}
