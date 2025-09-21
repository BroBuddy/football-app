package com.buddy.football.league.dto;

import com.buddy.football.nation.dto.NationDTO;

import java.util.UUID;

public record LeagueListDTO(
        UUID id,
        String name,
        NationDTO nation,
        double marketValue
) {
    public LeagueListDTO(UUID id, String name) {
        this(id, name, null, 0.0);
    }
}
