package com.buddy.football.util.match;

import java.util.Map;

import static java.util.Map.entry;

public class MatchWeights {

    public static final Map<String, Double> CONFIG = Map.ofEntries(
            entry("offense.xG", 1.8),
            entry("offense.xA", 0.8),
            entry("offense.xT", 1.0),
            entry("offense.successfulDribbles", 0.05),
            entry("offense.progressivePasses", 0.03),
            entry("offense.push", 1.6),
            entry("offense.factor", 225.0),
            entry("defense.tackles", 0.5),
            entry("defense.interceptions", 0.4),
            entry("defense.clearances", 0.3),
            entry("defense.blocks", 0.3),
            entry("defense.push", 1.6),
            entry("defense.factor", 225.0),
            entry("goals.min", 0.1),
            entry("goals.max", 3.0)
    );
}