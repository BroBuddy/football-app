package com.buddy.football.player.controller;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.player.dto.PlayerListDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.service.PlayerService;
import com.buddy.football.team.dto.TeamBaseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Disabled
class PlayerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void shouldReturnPagedPlayers() throws Exception {
        UUID playerId = UUID.randomUUID();
        Player player = new Player();
        player.setId(playerId);

        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "url");
        TeamBaseDTO teamBaseDTO = new TeamBaseDTO(UUID.randomUUID(), "Team Name", "logo.png");
        PlayerListDTO dto = new PlayerListDTO(playerId, "Test", "User", 25, 50.0, nationDTO, teamBaseDTO, "ST", 80);

        Page<Player> playerPage = new PageImpl<>(List.of(player));

        when(playerService.getAllPlayers(any(Pageable.class))).thenReturn(playerPage);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(dto);

        mockMvc.perform(get("/api/players")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(playerId.toString()));
    }

    @Test
    void shouldReturnPlayersByQuery() throws Exception {
        UUID playerId = UUID.randomUUID();
        Player player = new Player();
        player.setId(playerId);

        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "url");
        TeamBaseDTO teamBaseDTO = new TeamBaseDTO(UUID.randomUUID(), "Team Name", "logo.png");
        PlayerListDTO dto = new PlayerListDTO(playerId, "John", "Doe", 20, 50.0, nationDTO, teamBaseDTO, "ST", 80);

        Page<Player> playerPage = new PageImpl<>(List.of(player));

        when(playerService.findPlayersByFirstOrLastName(eq("Doe"), any(Pageable.class))).thenReturn(playerPage);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(dto);

        mockMvc.perform(get("/api/players")
                        .param("query", "Doe")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(playerId.toString()))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));
    }
}