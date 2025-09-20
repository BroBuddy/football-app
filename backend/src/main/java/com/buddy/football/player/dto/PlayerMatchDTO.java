package com.buddy.football.player.dto;

import java.util.Map;
import java.util.UUID;

public record PlayerMatchDTO(
        UUID id,
        String firstName,
        String lastName,
        boolean isStarting,
        Double marketValue,
        Map<String, Double> metrics
) {
}
