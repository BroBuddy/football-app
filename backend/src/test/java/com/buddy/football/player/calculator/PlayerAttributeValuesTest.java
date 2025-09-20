package com.buddy.football.player.calculator;

import com.buddy.football.player.attributes.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAttributeValuesTest {

    private PlayerAttributeValues attributeValues;
    private PlayerAttributes attributes;

    @BeforeEach
    void setup() {
        attributeValues = new PlayerAttributeValues();

        attributes = new PlayerAttributes(
                new AttackingAttributes(1, 2, 3, 4, 5),
                new SkillAttributes(6, 7, 8, 9, 10),
                new MovementAttributes(11, 12, 13, 14, 15),
                new PowerAttributes(16, 17, 18, 19, 20),
                new MentalityAttributes(21, 22, 23, 24, 25, 26),
                new DefendingAttributes(27, 28, 29),
                new GoalkeepingAttributes(30, 31, 32, 33, 34)
        );
    }

    @Test
    void testGetAttackingAttributes() {
        assertEquals(2, attributeValues.getAttributeValue(attributes, "finishing"));
        assertEquals(5, attributeValues.getAttributeValue(attributes, "volleys"));
        assertEquals(4, attributeValues.getAttributeValue(attributes, "shortPassing"));
        assertEquals(1, attributeValues.getAttributeValue(attributes, "crossing"));
        assertEquals(3, attributeValues.getAttributeValue(attributes, "headingAccuracy"));
    }

    @Test
    void testGetSkillAttributes() {
        assertEquals(9, attributeValues.getAttributeValue(attributes, "longPassing"));
        assertEquals(7, attributeValues.getAttributeValue(attributes, "curve"));
        assertEquals(10, attributeValues.getAttributeValue(attributes, "ballControl"));
        assertEquals(8, attributeValues.getAttributeValue(attributes, "freeKickAccuracy"));
        assertEquals(6, attributeValues.getAttributeValue(attributes, "dribbling"));
    }

    @Test
    void testGetMovementAttributes() {
        assertEquals(11, attributeValues.getAttributeValue(attributes, "acceleration"));
        assertEquals(12, attributeValues.getAttributeValue(attributes, "sprintSpeed"));
        assertEquals(13, attributeValues.getAttributeValue(attributes, "agility"));
        assertEquals(15, attributeValues.getAttributeValue(attributes, "balance"));
        assertEquals(14, attributeValues.getAttributeValue(attributes, "reactions"));
    }

    @Test
    void testGetPowerAttributes() {
        assertEquals(17, attributeValues.getAttributeValue(attributes, "jumping"));
        assertEquals(18, attributeValues.getAttributeValue(attributes, "stamina"));
        assertEquals(19, attributeValues.getAttributeValue(attributes, "strength"));
        assertEquals(16, attributeValues.getAttributeValue(attributes, "shotPower"));
        assertEquals(20, attributeValues.getAttributeValue(attributes, "longShots"));
    }

    @Test
    void testGetMentalityAttributes() {
        assertEquals(24, attributeValues.getAttributeValue(attributes, "vision"));
        assertEquals(26, attributeValues.getAttributeValue(attributes, "composure"));
        assertEquals(23, attributeValues.getAttributeValue(attributes, "attPosition"));
        assertEquals(25, attributeValues.getAttributeValue(attributes, "penalties"));
        assertEquals(22, attributeValues.getAttributeValue(attributes, "interceptions"));
        assertEquals(21, attributeValues.getAttributeValue(attributes, "aggression"));
    }

    @Test
    void testGetDefendingAttributes() {
        assertEquals(27, attributeValues.getAttributeValue(attributes, "defensiveAwareness"));
        assertEquals(28, attributeValues.getAttributeValue(attributes, "standingTackle"));
        assertEquals(29, attributeValues.getAttributeValue(attributes, "slidingTackle"));
    }

    @Test
    void testGetGoalkeepingAttributes() {
        assertEquals(30, attributeValues.getAttributeValue(attributes, "gkDiving"));
        assertEquals(31, attributeValues.getAttributeValue(attributes, "gkHandling"));
        assertEquals(32, attributeValues.getAttributeValue(attributes, "gkKicking"));
        assertEquals(33, attributeValues.getAttributeValue(attributes, "gkPositioning"));
        assertEquals(34, attributeValues.getAttributeValue(attributes, "gkReflexes"));
    }

    @Test
    void testUnknownAttributeKeyThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                attributeValues.getAttributeValue(attributes, "unknownKey")
        );
        assertTrue(exception.getMessage().contains("Unknown attribute key"));
    }
}
