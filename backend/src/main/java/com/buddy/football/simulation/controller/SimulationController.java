package com.buddy.football.simulation.controller;

import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.service.LeagueSimulationService;
import com.buddy.football.simulation.service.MatchSimulationService;
import com.buddy.football.team.dto.TeamListDTO;
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

    public SimulationController(
            MatchSimulationService matchSimulationService,
            LeagueSimulationService leagueService,
            TeamService teamService, MatchMapper matchMapper
    ) {
        this.matchSimulationService = matchSimulationService;
        this.leagueService = leagueService;
        this.teamService = teamService;
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

        Optional<MatchTeamDTO> match = teamService.getMatchupTeams(homeId, awayId);

        if (match.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(match.get());
    }

    @GetMapping("/league/{leagueId}")
    public List<Map<String, Object>> simulateLeague(@PathVariable UUID leagueId) {
        List<TeamListDTO> teams = teamService.getTeamsByLeagueId(leagueId);

        return leagueService.simulateLeague(teams);
    }
}