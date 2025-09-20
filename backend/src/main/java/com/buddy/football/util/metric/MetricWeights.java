package com.buddy.football.util.metric;

import java.util.Map;

public class MetricWeights {

    public static final Map<String, Double> XG = Map.of(
            "finishing", 0.30,
            "shotPower", 0.20,
            "attPosition", 0.20,
            "longShots", 0.10,
            "volleys", 0.10,
            "composure", 0.10
    );

    public static final Map<String, Double> XA = Map.of(
            "vision", 0.30,
            "shortPassing", 0.25,
            "longPassing", 0.20,
            "crossing", 0.15,
            "curve", 0.10
    );

    public static final Map<String, Double> XT = Map.of(
            "dribbling", 0.25,
            "ballControl", 0.20,
            "longPassing", 0.20,
            "vision", 0.15,
            "acceleration", 0.10,
            "sprintSpeed", 0.10
    );

    public static final Map<String, Double> PROGRESSIVE_PASSES = Map.of(
            "longPassing", 0.35,
            "vision", 0.30,
            "shortPassing", 0.20,
            "curve", 0.15
    );

    public static final Map<String, Double> SUCCESSFUL_DRIBBLES = Map.of(
            "dribbling", 0.30,
            "agility", 0.25,
            "ballControl", 0.20,
            "acceleration", 0.15,
            "balance", 0.10
    );

    public static final Map<String, Double> TACKLE_SUCCESS = Map.of(
            "standingTackle", 0.35,
            "slidingTackle", 0.25,
            "aggression", 0.20,
            "strength", 0.10,
            "balance", 0.10
    );

    public static final Map<String, Double> INTERCEPTIONS = Map.of(
            "interceptions", 0.40,
            "defensiveAwareness", 0.30,
            "aggression", 0.15,
            "reactions", 0.15
    );

    public static final Map<String, Double> CLEARANCES = Map.of(
            "headingAccuracy", 0.35,
            "jumping", 0.25,
            "strength", 0.20,
            "reactions", 0.10,
            "defensiveAwareness", 0.10
    );

    public static final Map<String, Double> PRESSURE_RECOVERY = Map.of(
            "stamina", 0.30,
            "aggression", 0.25,
            "acceleration", 0.20,
            "reactions", 0.15,
            "balance", 0.10
    );
}
