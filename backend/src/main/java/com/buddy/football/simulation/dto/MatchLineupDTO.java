package com.buddy.football.simulation.dto;

import com.buddy.football.player.dto.PlayerMatchDTO;

import java.util.List;
import java.util.UUID;

public record MatchLineupDTO(
        UUID id,
        String name,
        String logoUrl,
        List<PlayerMatchDTO> startingPlayers,
        double marketValue
) {
    public MatchLineupDTO(String name, List<PlayerMatchDTO> startingPlayers) {
        this(null, name, null, startingPlayers, 0.0);
    }
}
