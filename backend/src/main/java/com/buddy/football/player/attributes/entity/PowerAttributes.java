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
public class PowerAttributes {
    private Integer shotPower;
    private Integer jumping;
    private Integer stamina;
    private Integer strength;
    private Integer longShots;
}
