package com.buddy.football.league.service;

import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeagueServiceTest {

    private LeagueRepository leagueRepository;
    private LeagueMapper leagueMapper;
    private LeagueService leagueService;

    @BeforeEach
    void setUp() {
        leagueRepository = mock(LeagueRepository.class);
        leagueMapper = mock(LeagueMapper.class);
        leagueService = new LeagueService(leagueRepository, leagueMapper);
    }

    @Test
    void shouldReturnAllLeagues() {
        League league1 = new League();
        league1.setId(UUID.randomUUID());
        league1.setName("Bundesliga");
        League league2 = new League();
        league2.setId(UUID.randomUUID());
        league2.setName("Premier League");

        LeagueListDTO dto1 = new LeagueListDTO(league1.getId(), "Bundesliga", null, null);
        LeagueListDTO dto2 = new LeagueListDTO(league2.getId(), "Premier League", null, null);

        when(leagueRepository.findAllWithNationAndTeams()).thenReturn(List.of(league1, league2));

        when(leagueMapper.toListDTO(league1)).thenReturn(dto1);
        when(leagueMapper.toListDTO(league2)).thenReturn(dto2);

        List<LeagueListDTO> result = leagueService.getAllLeagues();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(l -> l.name().equals("Bundesliga")));
        assertTrue(result.stream().anyMatch(l -> l.name().equals("Premier League")));

        verify(leagueRepository, times(1)).findAllWithNationAndTeams();
        verify(leagueMapper, times(2)).toListDTO(any(League.class));
    }

    @Test
    void shouldReturnLeagueById() {
        UUID id = UUID.randomUUID();
        League league = new League();
        league.setId(id);
        league.setName("Serie A");

        when(leagueRepository.findById(id)).thenReturn(Optional.of(league));

        Optional<League> result = leagueService.getLeagueById(id);

        assertTrue(result.isPresent());
        assertEquals("Serie A", result.get().getName());
        verify(leagueRepository, times(1)).findById(id);
    }
}