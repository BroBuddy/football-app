package com.buddy.football.simmulation.service;

import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.service.MatchService;
import com.buddy.football.simulation.service.MatchSimulationService;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MatchSimulationServiceTest {

    private MatchMapper matchMapper;
    private TeamService teamService;
    private MatchSimulationService matchSimulationService;

    @BeforeEach
    void setup() {
        matchMapper = Mockito.mock(MatchMapper.class);
        teamService = Mockito.mock(TeamService.class);
        matchSimulationService = new MatchSimulationService(matchMapper, teamService);
    }

    @Test
    void testSimulateMatch_success() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        Team homeTeam = mock(Team.class);
        Team awayTeam = mock(Team.class);

        when(teamService.getTeamWithAllDetails(homeId)).thenReturn(Optional.of(homeTeam));
        when(teamService.getTeamWithAllDetails(awayId)).thenReturn(Optional.of(awayTeam));

        MatchLineupDTO homeLineup = mock(MatchLineupDTO.class);
        MatchLineupDTO awayLineup = mock(MatchLineupDTO.class);
        when(matchMapper.toLineupDTO(homeTeam)).thenReturn(homeLineup);
        when(matchMapper.toLineupDTO(awayTeam)).thenReturn(awayLineup);

        MatchResultDTO expectedResult = new MatchResultDTO("Home", 2, "Away", 1);

        try (var mocked = Mockito.mockStatic(MatchService.class)) {
            mocked.when(() -> MatchService.simulateMatch(any(MatchTeamDTO.class)))
                    .thenReturn(expectedResult);

            MatchResultDTO result = matchSimulationService.simulateMatch(homeId, awayId);

            assertEquals(expectedResult, result);
            mocked.verify(() -> MatchService.simulateMatch(any(MatchTeamDTO.class)), times(1));
        }
    }

    @Test
    void testSimulateMatch_homeTeamNotFound() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        when(teamService.getTeamWithAllDetails(homeId)).thenReturn(Optional.empty());
        when(teamService.getTeamWithAllDetails(awayId)).thenReturn(Optional.of(new Team()));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                matchSimulationService.simulateMatch(homeId, awayId));

        assertEquals("Home team not found", exception.getMessage());
    }

    @Test
    void testSimulateMatch_awayTeamNotFound() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        when(teamService.getTeamWithAllDetails(homeId)).thenReturn(Optional.of(new Team()));
        when(teamService.getTeamWithAllDetails(awayId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                matchSimulationService.simulateMatch(homeId, awayId));

        assertEquals("Away team not found", exception.getMessage());
    }
}