package com.buddy.football.league.dto;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.team.dto.TeamLeagueDTO;

import java.util.List;
import java.util.UUID;

public record LeagueListDTO(
        UUID id,
        String name,
        NationDTO nation,
        List<TeamLeagueDTO> teams
) {
    public LeagueListDTO(UUID id, String name) {
        this(id, name, null, null);
    }
}
