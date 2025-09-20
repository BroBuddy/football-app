package com.buddy.football.simulation.dto;

import java.util.UUID;

public record MatchResultTuple(
        UUID homeId,
        UUID awayId,
        int homeGoals,
        int awayGoals
) {
}
