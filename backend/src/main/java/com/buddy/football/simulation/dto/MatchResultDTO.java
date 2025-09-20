package com.buddy.football.simulation.dto;

public record MatchResultDTO(
        String homeTeam,
        int homeGoals,
        String awayTeam,
        int awayGoals
) {
}
