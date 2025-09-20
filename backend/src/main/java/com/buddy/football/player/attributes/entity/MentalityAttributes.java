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
public class MentalityAttributes {
    private Integer aggression;
    private Integer interceptions;
    private Integer attPosition;
    private Integer vision;
    private Integer penalties;
    private Integer composure;
}
