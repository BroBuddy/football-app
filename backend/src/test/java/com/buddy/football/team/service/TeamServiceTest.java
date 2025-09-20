package com.buddy.football.team.service;

import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    private TeamRepository teamRepository;
    private TeamService teamService;
    private TeamMapper teamMapper;

    @BeforeEach
    void setUp() {
        teamRepository = mock(TeamRepository.class);
        teamMapper = Mockito.mock(TeamMapper.class);
        teamService = new TeamService(teamRepository, teamMapper);
    }

    @Test
    void shouldReturnAllTeamsPagedAndSorted() {
        int page = 0;
        int size = 10;
        PageRequest sortedByMarketValueDesc = PageRequest.of(page, size, Sort.by("marketValue").descending());

        Team team1 = new Team();
        team1.setName("Bayer 04 Leverkusen");
        Team team2 = new Team();
        team2.setName("1. FC Köln");

        Page<Team> pageResult = new PageImpl<>(List.of(team1, team2));

        when(teamRepository.findAll(eq(sortedByMarketValueDesc))).thenReturn(pageResult);

        Page<Team> result = teamService.getAllTeams(page, size);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Bayer 04 Leverkusen", result.getContent().get(0).getName());

        verify(teamRepository).findAll(eq(sortedByMarketValueDesc));
    }

    @Test
    void shouldReturnTeamDetailsById() {
        UUID id = UUID.randomUUID();

        Team team = new Team();
        team.setId(id);
        team.setName("Bayer 04 Leverkusen");
        when(teamRepository.findTeamWithAllDetails(id)).thenReturn(Optional.of(team));

        TeamDetailDTO expectedDto = new TeamDetailDTO(
                id,
                "Bayer 04 Leverkusen",
                "bayer.png",
                200.0,
                List.of(),
                List.of()
        );
        when(teamMapper.toDetailDTO(team)).thenReturn(expectedDto);

        TeamDetailDTO result = teamService.getTeamDetails(id);

        assertNotNull(result);
        assertEquals("Bayer 04 Leverkusen", result.name());
        assertEquals(id, result.id());

        verify(teamRepository).findTeamWithAllDetails(id);
    }

    @Test
    void shouldReturnAllTeamsByLeagueId() {
        UUID leagueId = UUID.randomUUID();
        Team team1 = new Team();
        team1.setName("Bayer 04 Leverkusen");
        Team team2 = new Team();
        team2.setName("1. FC Köln");

        when(teamRepository.findAllByLeagueId(leagueId)).thenReturn(List.of(team1, team2));

        List<Team> result = teamService.getAllTeamsByLeagueId(leagueId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1. FC Köln", result.get(1).getName());
    }

    @Test
    void shouldReturnSortedTeamsByMarketValue() {
        Team teamA = mock(Team.class);
        when(teamA.getName()).thenReturn("Team A");
        when(teamA.getMarketValue()).thenReturn(150.0);

        Team teamB = mock(Team.class);
        when(teamB.getName()).thenReturn("Team B");
        when(teamB.getMarketValue()).thenReturn(200.0);

        Team teamC = mock(Team.class);
        when(teamC.getName()).thenReturn("Team C");
        when(teamC.getMarketValue()).thenReturn(100.0);

        when(teamRepository.findAll()).thenReturn(List.of(teamA, teamB, teamC));

        List<Team> sortedTeams = teamService.getSortedTeamsByMarketValue();

        assertNotNull(sortedTeams);
        assertEquals(3, sortedTeams.size());
        assertEquals("Team B", sortedTeams.get(0).getName());
        assertEquals(200.0, sortedTeams.get(0).getMarketValue());
        assertEquals("Team A", sortedTeams.get(1).getName());
        assertEquals(150.0, sortedTeams.get(1).getMarketValue());
        assertEquals("Team C", sortedTeams.get(2).getName());
        assertEquals(100.0, sortedTeams.get(2).getMarketValue());

        verify(teamRepository, times(1)).findAll();
    }
}