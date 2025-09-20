package com.buddy.football.player.calculator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPositionCalculatorTest {

    static class MockPositionWeights {
        static final Map<String, Map<String, Double>> ALL = new HashMap<>();
        static {
            ALL.put("gk", Map.of("goalkeeping", 1.0, "reflexes", 0.5));
            ALL.put("st", Map.of("shooting", 0.8, "pace", 0.5));
        }
    }

    private final PlayerPositionCalculator calc = new PlayerPositionCalculator();

    @Test
    void testCalculatePositionRatings() {
        Map<String, Integer> mainAttributes = Map.of(
                "goalkeeping", 90,
                "reflexes", 80,
                "shooting", 70,
                "pace", 60
        );

        Map<String, Integer> ratings = calc.calculatePositionRatings(mainAttributes);

        assertEquals(2, ratings.size());
        assertTrue(ratings.containsKey("gk"));
        assertTrue(ratings.containsKey("st"));

        assertTrue(ratings.get("gk") >= 10 && ratings.get("gk") <= 99);

        assertTrue(ratings.get("st") >= 10 && ratings.get("st") <= 99);
    }

    @Test
    void testMissingAttributeKey() {
        PlayerPositionCalculator calculator = new PlayerPositionCalculator();

        PlayerPositionWeights.ALL = Map.of(
                "gk", Map.of("goalkeeping", 1.0),
                "st", Map.of("shooting", 0.8, "pace", 0.5)
        );

        Map<String, Integer> mainAttributes = Map.of(
                "shooting", 70
        );

        Map<String, Integer> ratings = calculator.calculatePositionRatings(mainAttributes);

        assertEquals(2, ratings.size());
    }
}
