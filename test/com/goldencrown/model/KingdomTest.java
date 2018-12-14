package com.goldencrown.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KingdomTest {

    private Kingdom kingdom;

    @BeforeEach
    void setUp() {
        kingdom = new Kingdom("name", "emblem");
    }

    @Test
    void aKingdomShouldNotBeEqualToNull() {
        assertNotEquals(kingdom, null);
    }

    @Test
    void aKingdomShouldBeEqualToItself() {
        assertEquals(kingdom, kingdom);
    }

    @Test
    void aKingdomShouldNotBeEqualToStringKingdom() {
        assertNotEquals("Kingdom", kingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndEmblemShouldBeEqual() {
        Kingdom otherKingdom = new Kingdom("name", "emblem");

        assertEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndDifferentEmblemsShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name", "emblem1");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameEmblemAndDifferentNamesShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithDifferentNamesAndDifferentEmblemsShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndSameEmblemShouldHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name", "emblem");

        assertEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithSameNameAndDifferentEmblemsShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name", "emblem1");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithDifferentNamesAndSameEmblemShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithDifferentNamesAndDifferentEmblemsShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void aNullObjectCanNeverBecomeAnAlly() {
        Kingdom nullKingdom = null;

        kingdom.joinAllies(nullKingdom);

        assertNotEquals(1, kingdom.getAllies().size());
    }

    @Test
    void otherKingdomCanChooseToBecomeAnAlly() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(otherKingdom);

        assertTrue(kingdom.getAllies().contains(otherKingdom));
    }

    @Test
    void noDuplicatesAlliesShouldBeAdded() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(otherKingdom);
        kingdom.joinAllies(otherKingdom);

        assertNotEquals(2, kingdom.getAllies().size());
    }

    @Test
    void kingdomsWithSameNameAndEmblemShouldBeTreatedAsSingleAlly() {
        Kingdom kingdom1 = new Kingdom("name1", "emblem1");
        Kingdom kingdom2 = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(kingdom1);
        kingdom.joinAllies(kingdom2);

        assertNotEquals(2, kingdom.getAllies().size());
    }
}
