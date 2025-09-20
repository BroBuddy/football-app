package com.buddy.football.player.dto;

import com.buddy.football.nation.dto.NationDTO;

import java.util.UUID;

public record PlayerTeamDTO(
        UUID id,
        String firstName,
        String lastName,
        Integer age,
        Double marketValue,
        NationDTO nation,
        String mainPositions,
        Integer overallRating
) {
}
