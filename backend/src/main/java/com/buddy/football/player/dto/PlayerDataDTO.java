package com.buddy.football.player.dto;

import com.buddy.football.player.attributes.entity.*;

import java.time.LocalDate;
import java.util.Map;

public record PlayerDataDTO(
        String firstName,
        String lastName,
        LocalDate birthDate,
        int height,
        int weight,
        Double marketValue,
        String nationName,
        String teamName,
        boolean isStarting,
        AttackingAttributes attackingAttributes,
        SkillAttributes skillAttributes,
        MovementAttributes movementAttributes,
        PowerAttributes powerAttributes,
        MentalityAttributes mentalityAttributes,
        DefendingAttributes defendingAttributes,
        GoalkeepingAttributes goalkeepingAttributes
) {
}
