package com.buddy.football.player.attributes.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SkillAttributes {
    private Integer dribbling;
    private Integer curve;
    private Integer freeKickAccuracy;
    private Integer longPassing;
    private Integer ballControl;
}
