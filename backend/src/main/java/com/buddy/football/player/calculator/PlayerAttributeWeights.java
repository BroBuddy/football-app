package com.buddy.football.player.calculator;

import java.util.Map;

public class PlayerAttributeWeights {

    public enum AttributeCategory {
        SHOOTING, PASSING, PACE, DEFENDING, PHYSICAL, DRIBBLING, GOALKEEPING
    }

    private static final Map<String, Double> SHOOTING = Map.of(
            "finishing", 0.35,
            "attPosition", 0.25,
            "shotPower", 0.15,
            "volleys", 0.15,
            "longShots", 0.05,
            "penalties", 0.05
    );

    private static final Map<String, Double> PASSING = Map.of(
            "shortPassing", 0.25,
            "longPassing", 0.20,
            "vision", 0.20,
            "crossing", 0.15,
            "curve", 0.10,
            "freeKickAccuracy", 0.10
    );

    private static final Map<String, Double> PACE = Map.of(
            "acceleration", 0.55,
            "sprintSpeed", 0.45
    );

    private static final Map<String, Double> DEFENDING = Map.of(
            "defensiveAwareness", 0.35,
            "interceptions", 0.30,
            "standingTackle", 0.20,
            "slidingTackle", 0.10,
            "headingAccuracy", 0.05
    );

    private static final Map<String, Double> PHYSICAL = Map.of(
            "strength", 0.40,
            "stamina", 0.30,
            "jumping", 0.20,
            "aggression", 0.10
    );

    private static final Map<String, Double> DRIBBLING = Map.of(
            "dribbling", 0.30,
            "ballControl", 0.25,
            "agility", 0.20,
            "reactions", 0.15,
            "composure", 0.05,
            "balance", 0.05
    );

    private static final Map<String, Double> GOALKEEPING = Map.of(
            "gkReflexes", 0.30,
            "gkPositioning", 0.25,
            "gkDiving", 0.20,
            "gkHandling", 0.15,
            "gkKicking", 0.10
    );

    public static final Map<AttributeCategory, Map<String, Double>> ATTRIBUTE_WEIGHTS = Map.of(
            AttributeCategory.SHOOTING, SHOOTING,
            AttributeCategory.PASSING, PASSING,
            AttributeCategory.PACE, PACE,
            AttributeCategory.DEFENDING, DEFENDING,
            AttributeCategory.PHYSICAL, PHYSICAL,
            AttributeCategory.DRIBBLING, DRIBBLING,
            AttributeCategory.GOALKEEPING, GOALKEEPING
    );
}