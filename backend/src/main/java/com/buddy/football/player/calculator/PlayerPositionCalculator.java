package com.buddy.football.player.calculator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerPositionCalculator {

    public Map<String, Integer> calculatePositionRatings(
            Map<String, Integer> mainAttributes
    ) {
        Map<String, Integer> ratings = new HashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry : PlayerPositionWeights.ALL.entrySet()) {
            String position = entry.getKey();
            Map<String, Double> weights = entry.getValue();
            ratings.put(position, calculateForPosition(mainAttributes, weights, position));
        }

        return ratings.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(
                        java.util.stream.Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                java.util.LinkedHashMap::new
                        )
                );
    }

    private int calculateForPosition(
            Map<String, Integer> main,
            Map<String, Double> weights,
            String position
    ) {
        double total = 0.0;

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String key = entry.getKey();
            double weight = entry.getValue();

            if (main.containsKey(key)) {
                int attr = main.get(key);
                total += Math.pow(attr / 100.0, 1.0) * weight * 100;
            } else {
                System.out.println("⚠️ Missing value for key: " + key);
            }
        }

        if (!position.equalsIgnoreCase("gk")) {
            if (total >= 80) {
                total += 3;
            } else if (total >= 70) {
                total += 2;
            } else if (total >= 60) {
                total += 1;
            }
        }

        return Math.max(10, Math.min(99, (int) Math.round(total)));
    }
}
