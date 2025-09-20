package com.buddy.football.player.attributes.dto;

import com.buddy.football.player.attributes.entity.*;

public record PlayerAttributesDTO(
        AttackingAttributes attackingAttributes,
        SkillAttributes skillAttributes,
        MovementAttributes movementAttributes,
        PowerAttributes powerAttributes,
        MentalityAttributes mentalityAttributes,
        DefendingAttributes defendingAttributes,
        GoalkeepingAttributes goalkeepingAttributes
) {
}
