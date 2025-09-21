package com.buddy.football.util.metric;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.calculator.PlayerAttributeValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.buddy.football.util.metric.MetricWeights.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricCalculatorTest {

    @Mock
    private PlayerAttributeValues playerAttributeValues;

    @InjectMocks
    private MetricCalculator metricCalculator;

    private PlayerAttributes attributes;

    @BeforeEach
    void setUp() {
        attributes = new PlayerAttributes();
    }

    @Test
    void testCalculateXG() {
        mockAttributeValues(XG.keySet(), 90);
        double total = XG.values().stream().mapToDouble(weight -> 90 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 60.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateXG(attributes));
    }

    @Test
    void testCalculateXA() {
        mockAttributeValues(XA.keySet(), 85);
        double total = XA.values().stream().mapToDouble(weight -> 85 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 70.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateXA(attributes));
    }

    @Test
    void testCalculateXT() {
        mockAttributeValues(XT.keySet(), 80);
        double total = XT.values().stream().mapToDouble(weight -> 80 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 100.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateXT(attributes));
    }

    @Test
    void testCalculateProgressivePasses() {
        mockAttributeValues(PROGRESSIVE_PASSES.keySet(), 95);
        double total = PROGRESSIVE_PASSES.values().stream().mapToDouble(weight -> 95 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 10.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateProgressivePasses(attributes));
    }

    @Test
    void testCalculateSuccessfulDribbles() {
        mockAttributeValues(SUCCESSFUL_DRIBBLES.keySet(), 75);
        double total = SUCCESSFUL_DRIBBLES.values().stream().mapToDouble(weight -> 75 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 15.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateSuccessfulDribbles(attributes));
    }

    @Test
    void testCalculateTackleSuccess() {
        mockAttributeValues(TACKLE_SUCCESS.keySet(), 92);
        double total = TACKLE_SUCCESS.values().stream().mapToDouble(weight -> 92 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 80.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateTackleSuccess(attributes));
    }

    @Test
    void testCalculateInterceptions() {
        mockAttributeValues(INTERCEPTIONS.keySet(), 94);
        double total = INTERCEPTIONS.values().stream().mapToDouble(weight -> 94 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 120.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateInterceptions(attributes));
    }

    @Test
    void testCalculateClearances() {
        mockAttributeValues(CLEARANCES.keySet(), 91);
        double total = CLEARANCES.values().stream().mapToDouble(weight -> 91 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 110.0 / 15.0);
        assertEquals(expected, metricCalculator.calculateClearances(attributes));
    }

    @Test
    void testCalculatePressureRecovery() {
        mockAttributeValues(PRESSURE_RECOVERY.keySet(), 88);
        double total = PRESSURE_RECOVERY.values().stream().mapToDouble(weight -> 88 * weight).sum();
        double pushedTotal = Math.pow(total, 1.6);
        double expected = round(pushedTotal / 100.0 / 15.0);
        assertEquals(expected, metricCalculator.calculatePressureRecovery(attributes));
    }

    private void mockAttributeValues(java.util.Set<String> keys, int value) {
        keys.forEach(key ->
                when(playerAttributeValues.getAttributeValue(eq(attributes), eq(key))).thenReturn(value)
        );
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}