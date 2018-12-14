package com.goldencrown.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
}
