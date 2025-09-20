package com.buddy.football.player.positions.dto;

import com.buddy.football.player.positions.entity.PlayerPositions;

public class PlayerPositionsMapper {

    public static PlayerPositionsDTO fromEntity(PlayerPositions entity) {
        return new PlayerPositionsDTO(
                entity.getLm(),
                entity.getRw(),
                entity.getRm(),
                entity.getLw(),
                entity.getRb(),
                entity.getLb(),
                entity.getCm(),
                entity.getCb(),
                entity.getCdm(),
                entity.getCam(),
                entity.getSt(),
                entity.getGk()
        );
    }
}
