package com.buddy.football.team.dto;

import com.buddy.football.player.dto.PlayerTeamDTO;

import java.util.List;
import java.util.UUID;

public record TeamDetailDTO(
        UUID id,
        String name,
        String logoUrl,
        double marketValue,
        List<PlayerTeamDTO> startingPlayers,
        List<PlayerTeamDTO> restingPlayers
) {
    public TeamDetailDTO(UUID id, String name) {
        this(id, name, null, 0.0, null, null);
    }
}
