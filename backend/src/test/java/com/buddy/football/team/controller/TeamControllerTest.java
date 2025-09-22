package com.buddy.football.team.controller;

import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;

    @MockitoBean
    private TeamMapper teamMapper;

    @Test
    void shouldReturnPagedTeamsAndSortedByMarketValue() throws Exception {
        UUID teamId = UUID.randomUUID();
        Team team = new Team();
        team.setId(teamId);
        team.setName("Real Madrid");
        team.setLogoUrl("real-madrid.png");

        TeamListDTO teamDto = new TeamListDTO(
                teamId,
                "Real Madrid",
                0
        );

        when(teamService.getAllTeams(0, 10)).thenReturn(new PageImpl<>(List.of(team)));
        when(teamMapper.toListDTO(team)).thenReturn(teamDto);

        mockMvc.perform(get("/api/teams")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Real Madrid"));
    }

    @Test
    void shouldReturnTeamDetailsById() throws Exception {
        UUID id = UUID.randomUUID();

        TeamDetailDTO dto = new TeamDetailDTO(
                id,
                "Bayer 04 Leverkusen",
                "bayer.png",
                200.0,
                List.of(), // Starting players
                List.of()  // Resting players
        );

        when(teamService.getTeamDetails(id)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/teams/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Bayer 04 Leverkusen"));
    }

    @Test
    void shouldReturnNotFoundForUnknownTeam() throws Exception {
        UUID id = UUID.randomUUID();
        when(teamService.getTeamDetails(id)).thenReturn(null);

        mockMvc.perform(get("/api/teams/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
