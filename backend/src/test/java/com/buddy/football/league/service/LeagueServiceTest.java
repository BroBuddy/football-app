package com.buddy.football.league.service;

import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private LeagueMapper leagueMapper;

    @InjectMocks
    private LeagueService leagueService;

    @Test
    void shouldReturnAllLeagues() {
        // Given
        Nation nation1 = new Nation("Germany", "de.png");
        List<Team> teams1 = new ArrayList<>();

        League league1 = new League("Bundesliga", nation1, teams1, LocalDateTime.now(), LocalDateTime.now());
        league1.setId(UUID.randomUUID());

        Nation nation2 = new Nation("England", "en.png");
        List<Team> teams2 = new ArrayList<>();

        League league2 = new League("Premier League", nation2, teams2, LocalDateTime.now(), LocalDateTime.now());
        league2.setId(UUID.randomUUID());

        LeagueListDTO dto1 = new LeagueListDTO(league1.getId(), "Bundesliga", null, 0.0);
        LeagueListDTO dto2 = new LeagueListDTO(league2.getId(), "Premier League", null, 0.0);

        when(leagueRepository.findAll()).thenReturn(List.of(league1, league2));
        when(leagueMapper.toListDTO(league1)).thenReturn(dto1);
        when(leagueMapper.toListDTO(league2)).thenReturn(dto2);

        // When
        List<LeagueListDTO> result = leagueService.getAllLeagues();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(l -> l.name().equals("Bundesliga")));
        assertTrue(result.stream().anyMatch(l -> l.name().equals("Premier League")));

        verify(leagueRepository, times(1)).findAll();
        verify(leagueMapper, times(2)).toListDTO(any(League.class));
    }

    @Test
    void shouldReturnSortedLeagueById() {
        // Given
        UUID leagueId = UUID.randomUUID();
        Nation nation = new Nation("Germany", "de.png");

        Team teamA = new Team("Team A", null, 50000000.0, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        teamA.setId(UUID.randomUUID());

        Team teamB = new Team("Team B", null, 100000000.0, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        teamB.setId(UUID.randomUUID());

        Team teamC = new Team("Team C", null, 75000000.0, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        teamC.setId(UUID.randomUUID());

        List<Team> unsortedTeams = new ArrayList<>(List.of(teamA, teamB, teamC));

        League league = new League("Bundesliga", nation, unsortedTeams, LocalDateTime.now(), LocalDateTime.now());
        league.setId(leagueId);

        teamA.setLeague(league);
        teamB.setLeague(league);
        teamC.setLeague(league);

        when(leagueRepository.findById(leagueId)).thenReturn(Optional.of(league));

        // When
        Optional<League> result = leagueService.getLeagueById(leagueId);

        // Then
        assertTrue(result.isPresent());
        List<Team> sortedTeams = result.get().getTeams();

        assertEquals("Team B", sortedTeams.get(0).getName());
        assertEquals("Team C", sortedTeams.get(1).getName());
        assertEquals("Team A", sortedTeams.get(2).getName());

        verify(leagueRepository, times(1)).findById(leagueId);
    }
}