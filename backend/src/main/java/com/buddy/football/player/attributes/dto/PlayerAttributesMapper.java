package com.buddy.football.player.attributes.dto;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import org.springframework.stereotype.Component;

@Component
public class PlayerAttributesMapper {

    public static PlayerAttributesDTO fromEntity(PlayerAttributes entity) {
        return new PlayerAttributesDTO(
                entity.getAttackingAttributes(),
                entity.getSkillAttributes(),
                entity.getMovementAttributes(),
                entity.getPowerAttributes(),
                entity.getMentalityAttributes(),
                entity.getDefendingAttributes(),
                entity.getGoalkeepingAttributes()
        );
    }
}
