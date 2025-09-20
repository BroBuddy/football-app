package com.buddy.football.simulation.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TeamResult {
    public UUID id;
    public String name;
    public int games = 0;
    public int won = 0;
    public int draw = 0;
    public int lose = 0;
    public int goalsFor = 0;
    public int goalsAgainst = 0;
    public int points = 0;

    public TeamResult(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getDiff() {
        return goalsFor - goalsAgainst;
    }
}
