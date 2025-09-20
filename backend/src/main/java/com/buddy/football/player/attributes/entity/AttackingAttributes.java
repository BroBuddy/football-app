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
public class AttackingAttributes {
    private Integer crossing;
    private Integer finishing;
    private Integer headingAccuracy;
    private Integer shortPassing;
    private Integer volleys;
}
