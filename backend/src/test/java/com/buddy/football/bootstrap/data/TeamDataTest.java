package com.buddy.football.bootstrap.data;

import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeamDataTest {

    private LeagueRepository leagueRepository;

    @BeforeEach
    void setUp() {
        leagueRepository = Mockito.mock(LeagueRepository.class);

        Mockito.when(leagueRepository.findByName("Bundesliga"))
                .thenReturn(Optional.of(new League("Bundesliga", null, List.of(), null, null)));
        Mockito.when(leagueRepository.findByName("Premier League"))
                .thenReturn(Optional.of(new League("Premier League", null, List.of(), null, null)));
        Mockito.when(leagueRepository.findByName("La Liga"))
                .thenReturn(Optional.of(new League("La Liga", null, List.of(), null, null)));
        Mockito.when(leagueRepository.findByName("Serie A"))
                .thenReturn(Optional.of(new League("Serie A", null, List.of(), null, null)));
        Mockito.when(leagueRepository.findByName("Ligue 1"))
                .thenReturn(Optional.of(new League("Ligue 1", null, List.of(), null, null)));

        new TeamData(leagueRepository);
    }

    @Test
    void testGetAllTeams() {
        List<Team> teams = TeamData.get();
        assertNotNull(teams);
        assertFalse(teams.isEmpty());

        assertTrue(teams.stream().anyMatch(t -> t.getName().equals("FC Bayern München")));
        assertTrue(teams.stream().anyMatch(t -> t.getName().equals("Manchester City")));
        assertTrue(teams.stream().anyMatch(t -> t.getName().equals("Real Madrid")));
        assertTrue(teams.stream().anyMatch(t -> t.getName().equals("Juventus")));

        teams.forEach(t -> assertNotNull(t.getLogoUrl()));
    }

    @Test
    void testLeagueNotFoundThrows() {
        LeagueRepository mockRepo = Mockito.mock(LeagueRepository.class);
        Mockito.when(mockRepo.findByName("Bundesliga")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> new TeamData(mockRepo));

        assertTrue(ex.getMessage().contains("League not found"));
    }
}