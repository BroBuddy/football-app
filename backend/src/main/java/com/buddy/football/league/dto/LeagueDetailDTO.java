package com.buddy.football.league.dto;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.team.dto.TeamListDTO;

import java.util.List;
import java.util.UUID;

public record LeagueDetailDTO(
        UUID id,
        String name,
        NationDTO nation,
        List<TeamListDTO> teams
) {
    public LeagueDetailDTO(UUID id, String name) {
        this(id, name, null, null);
    }
}
