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
public class DefendingAttributes {
    private Integer defensiveAwareness;
    private Integer standingTackle;
    private Integer slidingTackle;
}
