package com.buddy.football.player.calculator;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.buddy.football.player.calculator.PlayerAttributeWeights.ATTRIBUTE_WEIGHTS;

@Component
public class PlayerAttributeCalculator {

    private final PlayerAttributeValues playerAttributeValues;

    public PlayerAttributeCalculator(PlayerAttributeValues playerAttributeValues) {
        this.playerAttributeValues = playerAttributeValues;
    }

    public Map<String, Integer> extractAttributes(PlayerAttributes attributes) {
        return Map.of(
                "pace", calculateAttribute(PlayerAttributeWeights.AttributeCategory.PACE, attributes),
                "shooting", calculateAttribute(PlayerAttributeWeights.AttributeCategory.SHOOTING, attributes),
                "passing", calculateAttribute(PlayerAttributeWeights.AttributeCategory.PASSING, attributes),
                "dribbling", calculateAttribute(PlayerAttributeWeights.AttributeCategory.DRIBBLING, attributes),
                "defending", calculateAttribute(PlayerAttributeWeights.AttributeCategory.DEFENDING, attributes),
                "physical", calculateAttribute(PlayerAttributeWeights.AttributeCategory.PHYSICAL, attributes),
                "goalkeeping", calculateAttribute(PlayerAttributeWeights.AttributeCategory.GOALKEEPING, attributes)
        );
    }

    public int calculateAttribute(PlayerAttributeWeights.AttributeCategory category, PlayerAttributes attributes) {
        if (category == null || !ATTRIBUTE_WEIGHTS.containsKey(category)) {
            throw new IllegalArgumentException("Unbekannte Kategorie: " + category);
        }

        Map<String, Double> weights = ATTRIBUTE_WEIGHTS.get(category);

        return calculateAttributeScore(attributes, weights);
    }

    private int calculateAttributeScore(PlayerAttributes attributes, Map<String, Double> weights) {
        double total = 0.0;

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String key = entry.getKey();
            double weight = entry.getValue();
            int value = playerAttributeValues.getAttributeValue(attributes, key);
            total += value * weight;
        }

        return (int) Math.floor(total);
    }
}
