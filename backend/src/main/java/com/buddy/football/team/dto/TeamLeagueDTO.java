package com.buddy.football.team.dto;

import java.util.UUID;

public record TeamLeagueDTO(
        UUID id,
        String name,
        String logoUrl,
        double marketValue
) {
    public TeamLeagueDTO(String name, double marketValue) {
        this(null, name, null, marketValue);
    }
}
