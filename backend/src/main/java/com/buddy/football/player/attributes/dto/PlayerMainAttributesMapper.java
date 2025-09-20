package com.buddy.football.player.attributes.dto;

import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import org.springframework.stereotype.Component;

@Component
public class PlayerMainAttributesMapper {
    public static PlayerMainAttributesDTO fromEntity(PlayerMainAttributes entity) {
        if (entity == null) return null;

        return new PlayerMainAttributesDTO(
                entity.getDribbling(),
                entity.getPace(),
                entity.getShooting(),
                entity.getDefending(),
                entity.getGoalkeeping(),
                entity.getPassing(),
                entity.getPhysical()
        );
    }
}

