package com.buddy.football.util.metric;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricWeightsTest {

    @Test
    void testXGWeights() {
        Map<String, Double> xg = MetricWeights.XG;
        assertEquals(6, xg.size());
        assertTrue(xg.containsKey("finishing"));
        assertTrue(xg.containsKey("shotPower"));
        assertTrue(xg.containsKey("attPosition"));
        assertTrue(xg.containsKey("longShots"));
        assertTrue(xg.containsKey("volleys"));
        assertTrue(xg.containsKey("composure"));

        double sum = xg.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(1.0, sum, 0.0001);
    }

    @Test
    void testXAWeights() {
        Map<String, Double> xa = MetricWeights.XA;
        assertEquals(5, xa.size());
        assertTrue(xa.containsKey("vision"));
        assertTrue(xa.containsKey("shortPassing"));
        assertTrue(xa.containsKey("longPassing"));
        assertTrue(xa.containsKey("crossing"));
        assertTrue(xa.containsKey("curve"));

        double sum = xa.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(1.0, sum, 0.0001);
    }

    @Test
    void testOtherWeightsSumToOne() {
        Map<String, Double>[] allMaps = new Map[]{
                MetricWeights.XT,
                MetricWeights.PROGRESSIVE_PASSES,
                MetricWeights.SUCCESSFUL_DRIBBLES,
                MetricWeights.TACKLE_SUCCESS,
                MetricWeights.INTERCEPTIONS,
                MetricWeights.CLEARANCES,
                MetricWeights.PRESSURE_RECOVERY
        };

        for (Map<String, Double> map : allMaps) {
            double sum = map.values().stream().mapToDouble(Double::doubleValue).sum();
            assertEquals(1.0, sum, 0.0001, "Sum of weights should be 1.0 for map: " + map);
        }
    }
}
