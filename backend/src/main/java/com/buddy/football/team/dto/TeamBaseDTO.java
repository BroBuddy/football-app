package com.buddy.football.team.dto;

import java.util.UUID;

public record TeamBaseDTO(
        UUID id,
        String name,
        String logoUrl
) {
    public TeamBaseDTO(UUID id, String name) {
        this(id, name, null);
    }
}
