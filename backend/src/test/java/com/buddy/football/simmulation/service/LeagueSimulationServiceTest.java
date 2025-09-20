package com.buddy.football.simmulation.service;

import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.service.LeagueSimulationService;
import com.buddy.football.simulation.service.MatchSimulationService;
import com.buddy.football.team.dto.TeamListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LeagueSimulationServiceTest {

    private MatchSimulationService matchSimulationService;
    private LeagueSimulationService leagueSimulationService;

    @BeforeEach
    void setup() {
        matchSimulationService = Mockito.mock(MatchSimulationService.class);
        leagueSimulationService = new LeagueSimulationService(matchSimulationService);
    }

    @Test
    void testSimulateLeague_basic() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        TeamListDTO team1 = new TeamListDTO(id1,"Bayer 04 Leverkusen", 500.0);
        TeamListDTO team2 = new TeamListDTO(id2,"1. FC Köln", 100.0);

        List<TeamListDTO> teams = List.of(team1, team2);

        when(matchSimulationService.simulateMatch(id1, id2))
                .thenReturn(new MatchResultDTO("Bayer 04 Leverkusen", 2, "1. FC Köln", 1));
        when(matchSimulationService.simulateMatch(id2, id1))
                .thenReturn(new MatchResultDTO("1. FC Köln", 3, "Bayer 04 Leverkusen", 0));

        List<Map<String, Object>> results = leagueSimulationService.simulateLeague(teams);

        assertEquals(2, results.size());

        Map<String, Object> firstTeam = results.get(0);
        Map<String, Object> secondTeam = results.get(1);

        assertEquals(3, firstTeam.get("points"));
        assertEquals(3, secondTeam.get("points"));

        assertEquals(2, firstTeam.get("games"));
        assertEquals(2, secondTeam.get("games"));

        assertEquals("4:2", firstTeam.get("goals"));
        assertEquals("2:4", secondTeam.get("goals"));
    }

    @Test
    void testSimulateLeague_draw() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        TeamListDTO team1 = new TeamListDTO(id1,"Bayer 04 Leverkusen", 500.0);
        TeamListDTO team2 = new TeamListDTO(id2,"1. FC Köln", 100.0);

        List<TeamListDTO> teams = List.of(team1, team2);

        when(matchSimulationService.simulateMatch(any(), any()))
                .thenReturn(new MatchResultDTO("Team A", 1, "Team B", 1));

        List<Map<String, Object>> results = leagueSimulationService.simulateLeague(teams);

        for (Map<String, Object> team : results) {
            assertEquals(2, team.get("games"));
            assertEquals(2, team.get("points"));
            assertEquals(2, team.get("draw"));
        }
    }
}