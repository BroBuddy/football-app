package com.buddy.football.player.dto;

public record PlayerFilterDTO(
        double marketValueMax,
        String position,
        int minValue
) {
}
