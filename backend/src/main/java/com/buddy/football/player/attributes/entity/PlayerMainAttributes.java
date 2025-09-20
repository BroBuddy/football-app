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
public class PlayerMainAttributes {
    private int dribbling;
    private int pace;
    private int shooting;
    private int defending;
    private int goalkeeping;
    private int passing;
    private int physical;
}
