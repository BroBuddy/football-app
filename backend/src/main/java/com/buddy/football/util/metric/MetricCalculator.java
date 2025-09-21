package com.buddy.football.util.metric;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.calculator.PlayerAttributeValues;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.buddy.football.util.metric.MetricWeights.*;

@Component
public class MetricCalculator {
    private static final double DIVISOR_XG = 60.0;
    private static final double DIVISOR_XA = 70.0;
    private static final double DIVISOR_XT = 100.0;
    private static final double DIVISOR_PROGRESSIVE_PASSES = 10.0;
    private static final double DIVISOR_SUCCESSFUL_DRIBBLES = 15.0;
    private static final double DIVISOR_TACKLE_SUCCESS = 80.0;
    private static final double DIVISOR_INTERCEPTIONS = 120.0;
    private static final double DIVISOR_CLEARANCES = 110.0;
    private static final double DIVISOR_PRESSURE_RECOVERY = 100.0;
    private static final double PUSH_OFFENSE = 1.6;
    private static final double PUSH_DEFENSE = 1.6;

    private final PlayerAttributeValues playerAttributeValues;

    public MetricCalculator(PlayerAttributeValues playerAttributeValues) {
        this.playerAttributeValues = playerAttributeValues;
    }

    public double calculateXG(PlayerAttributes attributes) {
        return calculateMetric(attributes, XG, DIVISOR_XG, PUSH_OFFENSE);
    }

    public double calculateXA(PlayerAttributes attributes) {
        return calculateMetric(attributes, XA, DIVISOR_XA, PUSH_OFFENSE);
    }

    public double calculateXT(PlayerAttributes attributes) {
        return calculateMetric(attributes, XT, DIVISOR_XT, PUSH_OFFENSE);
    }

    public double calculateProgressivePasses(PlayerAttributes attributes) {
        return calculateMetric(attributes, PROGRESSIVE_PASSES, DIVISOR_PROGRESSIVE_PASSES, PUSH_OFFENSE);
    }

    public double calculateSuccessfulDribbles(PlayerAttributes attributes) {
        return calculateMetric(attributes, SUCCESSFUL_DRIBBLES, DIVISOR_SUCCESSFUL_DRIBBLES, PUSH_OFFENSE);
    }

    public double calculateTackleSuccess(PlayerAttributes attributes) {
        return calculateMetric(attributes, TACKLE_SUCCESS, DIVISOR_TACKLE_SUCCESS, PUSH_DEFENSE);
    }

    public double calculateInterceptions(PlayerAttributes attributes) {
        return calculateMetric(attributes, INTERCEPTIONS, DIVISOR_INTERCEPTIONS, PUSH_DEFENSE);
    }

    public double calculateClearances(PlayerAttributes attributes) {
        return calculateMetric(attributes, CLEARANCES, DIVISOR_CLEARANCES, PUSH_DEFENSE);
    }

    public double calculatePressureRecovery(PlayerAttributes attributes) {
        return calculateMetric(attributes, PRESSURE_RECOVERY, DIVISOR_PRESSURE_RECOVERY, PUSH_DEFENSE);
    }

    private double calculateMetric(PlayerAttributes attributes, Map<String, Double> weights, double divisor, double pushFactor) {
        double total = 0.0;

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String key = entry.getKey();
            double weight = entry.getValue();
            int value = playerAttributeValues.getAttributeValue(attributes, key);

            total += value * weight;
        }

        double pushedTotal = Math.pow(total, pushFactor);

        return round(pushedTotal / divisor / 15);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}