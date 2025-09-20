package com.buddy.football.player.dto;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.player.attributes.dto.PlayerAttributesDTO;
import com.buddy.football.player.attributes.dto.PlayerMainAttributesDTO;
import com.buddy.football.team.dto.TeamBaseDTO;

import java.util.Map;
import java.util.UUID;

public record PlayerDetailDTO(
        UUID id,
        String firstName,
        String lastName,
        Integer age,
        int height,
        int weight,
        Double marketValue,
        NationDTO nation,
        TeamBaseDTO team,
        boolean isStarting,
        PlayerMainAttributesDTO mainAttributes,
        PlayerAttributesDTO attributes,
        Map<String, Integer> positions,
        String mainPositions,
        Integer overallRating,
        Map<String, Double> metrics
) {
    public PlayerDetailDTO(UUID id, String firstName, String lastName, PlayerAttributesDTO attributesDTO, PlayerMainAttributesDTO mainAttributesDTO, TeamBaseDTO teamDTO, NationDTO nationDTO, String positions, Map<String, Double> metrics) {
        this(id, firstName, lastName, null, 0, 0, 0.0, nationDTO, teamDTO, false, mainAttributesDTO, attributesDTO, null, positions, 0, metrics);
    }
}
