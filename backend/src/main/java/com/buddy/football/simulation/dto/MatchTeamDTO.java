package com.buddy.football.simulation.dto;

public record MatchTeamDTO(
        MatchLineupDTO home,
        MatchLineupDTO away
) {
}
