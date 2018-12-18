package com.tameofthrones.controller;

import com.tameofthrones.controller.helpers.KingdomFactory;
import com.tameofthrones.controller.helpers.UniverseInitializer;
import com.tameofthrones.model.Universe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniverseInitializerTest {

    @Test
    void shouldInitializeUniverseWithKingdomsFromKingdomMap() {
        Universe universe = UniverseInitializer.initialize();

        assertTrue(KingdomFactory.kingdomMap.values().containsAll(universe.getKingdoms()));
    }
}
