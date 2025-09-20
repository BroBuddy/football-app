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
public class MovementAttributes {
    private Integer acceleration;
    private Integer sprintSpeed;
    private Integer agility;
    private Integer reactions;
    private Integer balance;
}
