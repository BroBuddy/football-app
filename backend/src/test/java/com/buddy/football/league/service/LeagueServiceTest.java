package com.buddy.football.league.service;

import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.league.dto.LeagueMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private LeagueMapper leagueMapper;

    @InjectMocks
    private LeagueService leagueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllLeaguesAsDtoList() {
        // Given
        League league = new League();
        league.setId(UUID.randomUUID());
        league.setName("Test League");
        List<League> leagues = List.of(league);

        LeagueListDTO dto = new LeagueListDTO(league.getId(), league.getName());

        when(leagueRepository.findAll()).thenReturn(leagues);
        when(leagueMapper.toListDTO(league)).thenReturn(dto);

        // When
        List<LeagueListDTO> result = leagueService.getAllLeagues();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test League", result.get(0).name());
    }

    @Test
    void shouldReturnEmptyListWhenNoLeaguesExist() {
        // Given
        when(leagueRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<LeagueListDTO> result = leagueService.getAllLeagues();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}