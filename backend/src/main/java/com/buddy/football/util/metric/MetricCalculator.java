package com.buddy.football.util.metric;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.calculator.PlayerAttributeValues;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.buddy.football.util.metric.MetricWeights.*;

@Component
public class MetricCalculator {

    private final PlayerAttributeValues playerAttributeValues;

    public MetricCalculator(PlayerAttributeValues playerAttributeValues) {
        this.playerAttributeValues = playerAttributeValues;
    }

    public double calculateXG(PlayerAttributes attributes) {
        return calculateMetric(attributes, XG, 60.0);
    }

    public double calculateXA(PlayerAttributes attributes) {
        return calculateMetric(attributes, XA, 70.0);
    }

    public double calculateXT(PlayerAttributes attributes) {
        return calculateMetric(attributes, XT, 100.0);
    }

    public double calculateProgressivePasses(PlayerAttributes attributes) {
        return calculateMetric(attributes, PROGRESSIVE_PASSES, 10.0);
    }

    public double calculateSuccessfulDribbles(PlayerAttributes attributes) {
        return calculateMetric(attributes, SUCCESSFUL_DRIBBLES, 15.0);
    }

    public double calculateTackleSuccess(PlayerAttributes attributes) {
        return calculateMetric(attributes, TACKLE_SUCCESS, 80.0);
    }

    public double calculateInterceptions(PlayerAttributes attributes) {
        return calculateMetric(attributes, INTERCEPTIONS, 120.0);
    }

    public double calculateClearances(PlayerAttributes attributes) {
        return calculateMetric(attributes, CLEARANCES, 110.0);
    }

    public double calculatePressureRecovery(PlayerAttributes attributes) {
        return calculateMetric(attributes, PRESSURE_RECOVERY, 100.0);
    }

    private double calculateMetric(PlayerAttributes attributes, Map<String, Double> weights, double divisor) {
        double total = 0.0;

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String key = entry.getKey();
            double weight = entry.getValue();
            int value = playerAttributeValues.getAttributeValue(attributes, key);
            total += value * weight;
        }

        return round(total / divisor);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
