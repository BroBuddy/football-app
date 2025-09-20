package com.buddy.football.simulation.controller;

import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.service.LeagueSimulationService;
import com.buddy.football.simulation.service.MatchSimulationService;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    private final MatchSimulationService matchSimulationService;
    private final LeagueSimulationService leagueService;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final MatchMapper matchMapper;

    public SimulationController(
            MatchSimulationService matchSimulationService,
            LeagueSimulationService leagueService,
            TeamService teamService, TeamMapper teamMapper, MatchMapper matchMapper
    ) {
        this.matchSimulationService = matchSimulationService;
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.teamMapper = teamMapper;
        this.matchMapper = matchMapper;
    }

    @GetMapping
    public ResponseEntity<MatchResultDTO> simulateMatch(
            @RequestParam UUID homeId,
            @RequestParam UUID awayId) {
        MatchResultDTO result = matchSimulationService.simulateMatch(homeId, awayId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/match")
    public ResponseEntity<MatchTeamDTO> getMatchup(
            @RequestParam UUID homeId,
            @RequestParam UUID awayId) {

        Optional<Team> homeTeamOpt = teamService.getTeamWithAllDetails(homeId);
        Optional<Team> awayTeamOpt = teamService.getTeamWithAllDetails(awayId);

        if (homeTeamOpt.isEmpty() || awayTeamOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team homeTeam = homeTeamOpt.get();
        Team awayTeam = awayTeamOpt.get();

        MatchTeamDTO match = new MatchTeamDTO(
                matchMapper.toLineupDTO(homeTeam),
                matchMapper.toLineupDTO(awayTeam)
        );

        return ResponseEntity.ok(match);
    }

    @GetMapping("/league/{leagueId}")
    public List<Map<String, Object>> simulateLeague(@PathVariable UUID leagueId) {
        List<TeamListDTO> teams = teamService.getAllTeamsByLeagueId(leagueId).stream()
                .map(teamMapper::toListDTO)
                .toList();

        return leagueService.simulateLeague(teams);
    }
}

