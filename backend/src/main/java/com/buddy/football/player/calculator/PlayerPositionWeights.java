package com.buddy.football.player.calculator;

import java.util.Map;

public class PlayerPositionWeights {

    public static final Map<String, Double> GK = Map.of(
            "goalkeeping", 1.0
    );

    public static final Map<String, Double> CB = Map.of(
            "defending", 0.50,
            "physical", 0.25,
            "pace", 0.12,
            "passing", 0.08,
            "dribbling", 0.03,
            "shooting", 0.02
    );

    public static final Map<String, Double> LB = Map.of(
            "defending", 0.40,
            "pace", 0.30,
            "physical", 0.15,
            "passing", 0.10,
            "dribbling", 0.03,
            "shooting", 0.02
    );

    public static final Map<String, Double> RB = Map.of(
            "defending", 0.40,
            "pace", 0.30,
            "physical", 0.15,
            "passing", 0.10,
            "dribbling", 0.03,
            "shooting", 0.02
    );

    public static final Map<String, Double> CDM = Map.of(
            "defending", 0.30,
            "physical", 0.25,
            "passing", 0.25,
            "dribbling", 0.12,
            "pace", 0.05,
            "shooting", 0.03
    );

    public static final Map<String, Double> CM = Map.of(
            "passing", 0.40,
            "dribbling", 0.25,
            "defending", 0.15,
            "physical", 0.10,
            "pace", 0.06,
            "shooting", 0.04
    );

    public static final Map<String, Double> LM = Map.of(
            "pace", 0.35,
            "passing", 0.33,
            "dribbling", 0.15,
            "physical", 0.10,
            "shooting", 0.04,
            "defending", 0.03
    );

    public static final Map<String, Double> RM = Map.of(
            "pace", 0.35,
            "passing", 0.33,
            "dribbling", 0.15,
            "physical", 0.10,
            "shooting", 0.04,
            "defending", 0.03
    );

    public static final Map<String, Double> CAM = Map.of(
            "shooting", 0.32,
            "passing", 0.28,
            "dribbling", 0.25,
            "pace", 0.08,
            "physical", 0.05,
            "defending", 0.02
    );

    public static final Map<String, Double> LW = Map.of(
            "pace", 0.30,
            "dribbling", 0.35,
            "shooting", 0.15,
            "passing", 0.10,
            "physical", 0.05,
            "defending", 0.05
    );

    public static final Map<String, Double> RW = Map.of(
            "pace", 0.30,
            "dribbling", 0.35,
            "shooting", 0.15,
            "passing", 0.10,
            "physical", 0.05,
            "defending", 0.05
    );

    public static final Map<String, Double> ST = Map.of(
            "shooting", 0.50,
            "physical", 0.20,
            "pace", 0.14,
            "dribbling", 0.10,
            "passing", 0.05,
            "defending", 0.01
    );

    public static Map<String, Map<String, Double>> ALL = Map.ofEntries(
            Map.entry("GK", GK),
            Map.entry("CB", CB),
            Map.entry("LB", LB),
            Map.entry("RB", RB),
            Map.entry("CDM", CDM),
            Map.entry("CM", CM),
            Map.entry("LM", LM),
            Map.entry("RM", RM),
            Map.entry("CAM", CAM),
            Map.entry("LW", LW),
            Map.entry("RW", RW),
            Map.entry("ST", ST)
    );
}
