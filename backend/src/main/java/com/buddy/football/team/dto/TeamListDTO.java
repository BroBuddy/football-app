package com.buddy.football.team.dto;

import java.util.UUID;

public record TeamListDTO(
        UUID id,
        String name,
        String logoUrl,
        Double marketValue,
        Integer playerCount
) {
    public TeamListDTO(String name, double marketValue) {
        this(null, name, null, marketValue, null);
    }

    public TeamListDTO(UUID id, String name, double marketValue) {
        this(id, name, null, marketValue, null);
    }
}
