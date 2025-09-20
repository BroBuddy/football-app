package com.buddy.football.util.metric;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.calculator.PlayerAttributeValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static com.buddy.football.util.metric.MetricWeights.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MetricCalculatorTest {

    private PlayerAttributeValues playerAttributeValues;
    private MetricCalculator metricCalculator;
    private PlayerAttributes attributes;

    @BeforeEach
    void setUp() {
        playerAttributeValues = mock(PlayerAttributeValues.class);
        metricCalculator = new MetricCalculator(playerAttributeValues);
        attributes = new PlayerAttributes();
    }

    @Test
    void testCalculateXG() {
        XG.keySet().forEach(key ->
                when(playerAttributeValues.getAttributeValue(eq(attributes), eq(key))).thenReturn(10)
        );

        double result = metricCalculator.calculateXG(attributes);

        double expectedTotal = XG.values().stream().mapToDouble(w -> w * 10).sum() / 60.0;
        expectedTotal = Math.round(expectedTotal * 10.0) / 10.0;

        assertEquals(expectedTotal, result);

        verify(playerAttributeValues, times(XG.size())).getAttributeValue(eq(attributes), anyString());
    }

    @Test
    void testCalculateXA() {
        XA.keySet().forEach(key ->
                when(playerAttributeValues.getAttributeValue(eq(attributes), eq(key))).thenReturn(5)
        );

        double result = metricCalculator.calculateXA(attributes);

        double expectedTotal = XA.values().stream().mapToDouble(w -> w * 5).sum() / 70.0;
        expectedTotal = Math.round(expectedTotal * 10.0) / 10.0;

        assertEquals(expectedTotal, result);

        verify(playerAttributeValues, times(XA.size())).getAttributeValue(eq(attributes), anyString());
    }

    @Test
    void testRound() throws Exception {
        Method roundMethod = MetricCalculator.class.getDeclaredMethod("round", double.class);
        roundMethod.setAccessible(true);

        double value = 12.345;
        double result = (double) roundMethod.invoke(metricCalculator, value);

        assertEquals(12.3, result);
    }
}
