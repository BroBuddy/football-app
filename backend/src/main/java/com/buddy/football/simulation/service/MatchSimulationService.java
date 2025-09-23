package com.buddy.football.simulation.service;

import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MatchSimulationService {

    private final MatchMapper matchMapper;
    private final TeamService teamService;

    public MatchSimulationService(MatchMapper matchMapper, TeamService teamService) {
        this.matchMapper = matchMapper;
        this.teamService = teamService;
    }

    public MatchResultDTO simulateMatch(UUID homeId, UUID awayId) {
        Team homeTeam = teamService.getTeamWithAllDetails(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home team not found"));
        Team awayTeam = teamService.getTeamWithAllDetails(awayId)
                .orElseThrow(() -> new IllegalArgumentException("Away team not found"));

        MatchTeamDTO match = new MatchTeamDTO(
                matchMapper.toLineupDTO(homeTeam),
                matchMapper.toLineupDTO(awayTeam)
        );

        return MatchService.simulateMatch(match);
    }
}

