package com.buddy.football.simmulation.controller;

import com.buddy.football.simulation.controller.SimulationController;
import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.service.LeagueSimulationService;
import com.buddy.football.simulation.service.MatchSimulationService;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimulationControllerTest {

    private MatchSimulationService matchSimulationService;
    private LeagueSimulationService leagueService;
    private TeamService teamService;
    private TeamMapper teamMapper;
    private MatchMapper matchMapper;

    private SimulationController controller;

    @BeforeEach
    void setup() {
        matchSimulationService = mock(MatchSimulationService.class);
        leagueService = mock(LeagueSimulationService.class);
        teamService = mock(TeamService.class);
        teamMapper = mock(TeamMapper.class);
        matchMapper = mock(MatchMapper.class);

        controller = new SimulationController(
                matchSimulationService,
                leagueService,
                teamService,
                teamMapper,
                matchMapper
        );
    }

    @Test
    void testGetMatchup_success() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        Team homeTeam = mock(Team.class);
        Team awayTeam = mock(Team.class);

        when(teamService.getTeamWithAllDetails(homeId)).thenReturn(Optional.of(homeTeam));
        when(teamService.getTeamWithAllDetails(awayId)).thenReturn(Optional.of(awayTeam));

        MatchLineupDTO homeLineupMock = mock(MatchLineupDTO.class);
        MatchLineupDTO awayLineupMock = mock(MatchLineupDTO.class);

        when(matchMapper.toLineupDTO(homeTeam)).thenReturn(homeLineupMock);
        when(matchMapper.toLineupDTO(awayTeam)).thenReturn(awayLineupMock);

        ResponseEntity<MatchTeamDTO> response = controller.getMatchup(homeId, awayId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(homeLineupMock, response.getBody().home());
        assertEquals(awayLineupMock, response.getBody().away());

        verify(teamService, times(1)).getTeamWithAllDetails(homeId);
        verify(teamService, times(1)).getTeamWithAllDetails(awayId);
    }

    @Test
    void testGetMatchup_notFound() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        when(teamService.getTeamWithAllDetails(any())).thenReturn(Optional.empty());

        ResponseEntity<MatchTeamDTO> response = controller.getMatchup(homeId, awayId);

        assertEquals(404, response.getStatusCodeValue());

        verify(teamService, times(1)).getTeamWithAllDetails(homeId);
    }

    @Test
    void testSimulateLeague() {
        UUID leagueId = UUID.randomUUID();
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);

        when(teamService.getAllTeamsByLeagueId(leagueId)).thenReturn(List.of(team1, team2));
        when(teamMapper.toListDTO(team1)).thenReturn(new TeamListDTO(UUID.randomUUID(), "Team1", 100.0));
        when(teamMapper.toListDTO(team2)).thenReturn(new TeamListDTO(UUID.randomUUID(), "Team2", 200.0));

        List<Map<String, Object>> leagueResult = List.of(
                Map.of("team", "Team1", "points", 3),
                Map.of("team", "Team2", "points", 3)
        );
        when(leagueService.simulateLeague(any())).thenReturn(leagueResult);

        List<Map<String, Object>> response = controller.simulateLeague(leagueId);

        assertEquals(2, response.size());
        assertEquals(leagueResult, response);
    }

    @Test
    void testSimulateMatch_success() {
        UUID homeId = UUID.randomUUID();
        String homeTeam = "Bayer 04 Leverkusen";
        UUID awayId = UUID.randomUUID();
        String awayTeam = "1. FC Köln";

        MatchResultDTO mockResult = new MatchResultDTO(
                homeTeam, 2, awayTeam, 1
        );

        when(matchSimulationService.simulateMatch(homeId, awayId)).thenReturn(mockResult);

        ResponseEntity<MatchResultDTO> response = controller.simulateMatch(homeId, awayId);

        assertEquals(200, response.getStatusCodeValue());

        assertNotNull(response.getBody());

        assertEquals(mockResult, response.getBody());

        verify(matchSimulationService, times(1)).simulateMatch(homeId, awayId);
    }
}
