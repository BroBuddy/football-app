package com.buddy.football.simulation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamStats {
    public double xA;
    public double xT;
    public double xG;
    public double progressivePasses;
    public double successfulDribbles;
    public double interceptions;
    public double tackles;
    public double blocks;
    public double clearances;
}