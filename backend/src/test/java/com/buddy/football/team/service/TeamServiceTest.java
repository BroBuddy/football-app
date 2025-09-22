package com.buddy.football.team.service;

import com.buddy.football.player.entity.Player;
import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private UUID teamId;

    @BeforeEach
    void setUp() {
        teamId = UUID.randomUUID();
        team = new Team();
        team.setId(teamId);
        team.setMarketValue(1000.0);
    }

    @Test
    void getAllTeams_shouldReturnSortedPage() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by("marketValue").descending());
        Page<Team> teamPage = new PageImpl<>(List.of(team));
        when(teamRepository.findAll(sortedPageable)).thenReturn(teamPage);

        // Act
        Page<Team> result = teamService.getAllTeams(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(teamRepository).findAll(sortedPageable);
    }

    @Test
    void getAllTeamsByLeagueId_shouldReturnListOfTeams() {
        // Arrange
        UUID leagueId = UUID.randomUUID();
        List<Team> teamList = List.of(team);
        when(teamRepository.findAllByLeagueId(leagueId)).thenReturn(teamList);

        // Act
        List<Team> result = teamService.getAllTeamsByLeagueId(leagueId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(teamRepository).findAllByLeagueId(leagueId);
    }

    @Test
    void getSortedTeamsByMarketValue_shouldReturnTeamsSortedByMarketValueDesc() {
        // Arrange
        Team team1 = new Team();
        team1.setId(UUID.randomUUID());
        team1.setMarketValue(5000.0);

        Team team2 = new Team();
        team2.setId(UUID.randomUUID());
        team2.setMarketValue(10000.0);

        Team team3 = new Team();
        team3.setId(UUID.randomUUID());
        team3.setMarketValue(7500.0);

        List<Team> unsortedTeams = List.of(team1, team2, team3);
        when(teamRepository.findAll()).thenReturn(unsortedTeams);

        // Act
        List<Team> result = teamService.getSortedTeamsByMarketValue();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(10000.0, result.get(0).getMarketValue());
        assertEquals(7500.0, result.get(1).getMarketValue());
        assertEquals(5000.0, result.get(2).getMarketValue());
        verify(teamRepository).findAll();
    }

    @Test
    void updateTeamStats_shouldUpdateMarketValueAndSave() {
        // Arrange
        Player player1 = new Player();
        player1.setMarketValue(50.0);
        Player player2 = new Player();
        player2.setMarketValue(75.0);
        List<Player> players = List.of(player1, player2);

        ArgumentCaptor<Team> teamCaptor = ArgumentCaptor.forClass(Team.class);

        // Act
        teamService.updateTeamStats(team, players);

        // Assert
        verify(teamRepository).save(teamCaptor.capture());
        Team savedTeam = teamCaptor.getValue();
        assertEquals(125.0, savedTeam.getMarketValue());
    }

    @Test
    void updateTeamStats_withNullPlayers_shouldDoNothing() {
        // Act
        teamService.updateTeamStats(team, null);

        // Assert
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void updateTeamStats_withEmptyPlayers_shouldDoNothing() {
        // Act
        teamService.updateTeamStats(team, Collections.emptyList());

        // Assert
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void getTeamDetails_shouldReturnTeamDetailDTO() {
        // Arrange
        UUID testTeamId = UUID.randomUUID();
        String testTeamName = "Test Team";
        TeamDetailDTO expectedDTO = new TeamDetailDTO(testTeamId, testTeamName);        when(teamRepository.findTeamWithAllDetails(teamId)).thenReturn(Optional.of(team));
        when(teamMapper.toDetailDTO(any(Team.class))).thenReturn(expectedDTO);

        // Act
        TeamDetailDTO result = teamService.getTeamDetails(teamId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(teamRepository).findTeamWithAllDetails(teamId);
        verify(teamMapper).toDetailDTO(team);
    }

    @Test
    void getTeamDetails_shouldReturnNull_whenTeamNotFound() {
        // Arrange
        when(teamRepository.findTeamWithAllDetails(teamId)).thenReturn(Optional.empty());

        // Act
        TeamDetailDTO result = teamService.getTeamDetails(teamId);

        // Assert
        assertNull(result);
        verify(teamRepository).findTeamWithAllDetails(teamId);
        verify(teamMapper, never()).toDetailDTO(any(Team.class));
    }

    @Test
    void getTeamWithAllDetails_shouldReturnTeamOptional() {
        // Arrange
        when(teamRepository.findTeamWithAllDetails(teamId)).thenReturn(Optional.of(team));

        // Act
        Optional<Team> result = teamService.getTeamWithAllDetails(teamId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(team, result.get());
        verify(teamRepository).findTeamWithAllDetails(teamId);
    }

    @Test
    void getTeamWithAllDetails_shouldReturnEmptyOptional_whenTeamNotFound() {
        // Arrange
        when(teamRepository.findTeamWithAllDetails(teamId)).thenReturn(Optional.empty());

        // Act
        Optional<Team> result = teamService.getTeamWithAllDetails(teamId);

        // Assert
        assertFalse(result.isPresent());
        verify(teamRepository).findTeamWithAllDetails(teamId);
    }
}