package com.buddy.football.player.dto;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.team.dto.TeamBaseDTO;

import java.util.UUID;

public record PlayerListDTO(
        UUID id,
        String firstName,
        String lastName,
        Integer age,
        Double marketValue,
        NationDTO nation,
        TeamBaseDTO team,
        String mainPositions,
        Integer overallRating
) {
    public PlayerListDTO(UUID id, String firstName, String lastName, int age, double marketValue, TeamBaseDTO teamDTO, NationDTO nationDTO) {
        this(id, firstName, lastName, age, marketValue, nationDTO, teamDTO, null, null);
    }
}
