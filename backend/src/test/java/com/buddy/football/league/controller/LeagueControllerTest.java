package com.buddy.football.league.controller;

import com.buddy.football.league.dto.LeagueDetailDTO;
import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.service.LeagueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LeagueController.class)
class LeagueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeagueService leagueService;

    @MockitoBean
    private LeagueMapper leagueMapper;

    @Test
    void shouldReturnAllLeagues() throws Exception {
        LeagueListDTO dto = new LeagueListDTO(UUID.randomUUID(), "Bundesliga");

        when(leagueService.getAllLeagues()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/leagues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bundesliga"));
    }

    @Test
    void shouldReturnLeagueById() throws Exception {
        UUID id = UUID.randomUUID();

        LeagueDetailDTO dto = new LeagueDetailDTO(id, "Premier League");

        when(leagueService.getLeagueById(id)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/leagues/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Premier League"))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnNotFoundForUnknownId() throws Exception {
        UUID id = UUID.randomUUID();
        when(leagueService.getLeagueById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/leagues/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        when(leagueService.getAllLeagues()).thenReturn(List.of());

        mockMvc.perform(get("/api/leagues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnBadRequestForMalformedId() throws Exception {
        mockMvc.perform(get("/api/leagues/not-a-valid-uuid"))
                .andExpect(status().isBadRequest());
    }
}