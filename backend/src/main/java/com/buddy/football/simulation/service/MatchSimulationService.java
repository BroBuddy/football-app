package com.buddy.football.simulation.service;

import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchTeamDTO;
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
        MatchTeamDTO matchup = teamService.getMatchupTeams(homeId, awayId)
                .orElseThrow(() -> new IllegalArgumentException("One or both teams not found."));

        return new MatchResultDTO(
                matchup.home().name(),
                0,
                matchup.away().name(),
                0
        );
    }
}