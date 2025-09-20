package com.buddy.football.player.dto;

import java.util.Map;

public record PlayerCalculations(
        Map<String, Double> metrics
) {}
