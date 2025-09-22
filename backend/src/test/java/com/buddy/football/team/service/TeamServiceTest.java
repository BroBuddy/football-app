package com.buddy.football.team.service;

import com.buddy.football.player.entity.Player;
import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMapper teamMapper;
    @Mock
    private MatchMapper matchMapper;

    @InjectMocks
    private TeamService teamService;

    @Test
    void shouldReturnPageOfTeamsSortedByMarketValue() {
        int page = 0;
        int size = 10;
        Pageable expectedPageable = PageRequest.of(page, size, Sort.by("marketValue").descending());
        Page<Team> teamPage = new PageImpl<>(Collections.singletonList(new Team()));

        when(teamRepository.findAll(any(Pageable.class))).thenReturn(teamPage);

        Page<Team> result = teamService.getAllTeams(page, size);

        assertEquals(teamPage, result);
        verify(teamRepository).findAll(expectedPageable);
    }

    @Test
    void shouldUpdateTeamMarketValueAndSaveTeam() {
        Team team = new Team();

        Player player1 = new Player();
        player1.setMarketValue(10000000.0);

        Player player2 = new Player();
        player2.setMarketValue(20000000.0);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        double expectedTotalValue = 30000000.0;

        teamService.updateTeamStats(team, players);

        assertEquals(expectedTotalValue, team.getMarketValue());
        verify(teamRepository).save(team);
    }

    @Test
    void shouldNotChangeMarketValueWhenPlayersListIsEmpty() {
        Team team = new Team();
        team.setMarketValue(100.0);
        List<Player> players = new ArrayList<>();

        teamService.updateTeamStats(team, players);

        assertEquals(100.0, team.getMarketValue());
    }

    @Test
    void shouldReturnTeamDetailsDTOWhenTeamExists() {
        UUID teamId = UUID.randomUUID();
        Team team = new Team();
        team.setId(teamId);
        TeamDetailDTO dto = new TeamDetailDTO(teamId, "Test Team");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(teamMapper.toDetailDTO(team)).thenReturn(dto);

        Optional<TeamDetailDTO> result = teamService.getTeamDetails(teamId);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenTeamDoesNotExist() {
        UUID teamId = UUID.randomUUID();
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        Optional<TeamDetailDTO> result = teamService.getTeamDetails(teamId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnListOfTeamListDTOsByLeagueId() {
        UUID leagueId = UUID.randomUUID();
        TeamListDTO teamDto = new TeamListDTO(UUID.randomUUID(), "Team A", "a.png", 1000000.0, 25);
        List<TeamListDTO> expectedList = List.of(teamDto);

        when(teamRepository.findTeamsByLeagueId(leagueId)).thenReturn(expectedList);

        List<TeamListDTO> result = teamService.getTeamsByLeagueId(leagueId);

        assertEquals(expectedList, result);
    }

    @Test
    void shouldReturnMatchTeamDTOWhenBothTeamsExist() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();
        Team homeTeam = new Team();
        homeTeam.setId(homeId);
        Team awayTeam = new Team();
        awayTeam.setId(awayId);

        MatchLineupDTO homeLineup = Mockito.mock(MatchLineupDTO.class);
        MatchLineupDTO awayLineup = Mockito.mock(MatchLineupDTO.class);

        when(teamRepository.findById(homeId)).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findById(awayId)).thenReturn(Optional.of(awayTeam));
        when(matchMapper.toLineupDTO(homeTeam)).thenReturn(homeLineup);
        when(matchMapper.toLineupDTO(awayTeam)).thenReturn(awayLineup);

        Optional<MatchTeamDTO> result = teamService.getMatchupTeams(homeId, awayId);

        assertTrue(result.isPresent());
        MatchTeamDTO dto = result.get();

        assertEquals(homeLineup, dto.home());
        assertEquals(awayLineup, dto.away());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHomeTeamDoesNotExist() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        when(teamRepository.findById(homeId)).thenReturn(Optional.empty());
        when(teamRepository.findById(awayId)).thenReturn(Optional.of(new Team()));

        Optional<MatchTeamDTO> result = teamService.getMatchupTeams(homeId, awayId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalWhenAwayTeamDoesNotExist() {
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        when(teamRepository.findById(homeId)).thenReturn(Optional.of(new Team()));
        when(teamRepository.findById(awayId)).thenReturn(Optional.empty());

        Optional<MatchTeamDTO> result = teamService.getMatchupTeams(homeId, awayId);

        assertFalse(result.isPresent());
    }
}