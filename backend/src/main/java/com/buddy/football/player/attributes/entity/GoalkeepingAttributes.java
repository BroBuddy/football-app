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
public class GoalkeepingAttributes {
    private Integer gkDiving;
    private Integer gkHandling;
    private Integer gkKicking;
    private Integer gkPositioning;
    private Integer gkReflexes;
}
