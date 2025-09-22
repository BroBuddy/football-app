package com.buddy.football.player.controller;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.player.dto.PlayerDetailDTO;
import com.buddy.football.player.dto.PlayerListDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.service.PlayerService;
import com.buddy.football.team.dto.TeamBaseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerController playerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void getPlayers_withoutQuery_shouldReturnAllPlayers() throws Exception {
        Player player = new Player();
        TeamBaseDTO teamDTO = new TeamBaseDTO(UUID.randomUUID(), "Test Team", "Abbr");
        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Test Nation");
        PlayerListDTO playerDTO = new PlayerListDTO(UUID.randomUUID(), "Test", "Player", 25, 50.0, teamDTO, nationDTO);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "overallRating"));

        Page<Player> playerPage = new PageImpl<>(List.of(player), pageable, 1);

        when(playerService.getAllPlayers(any(Pageable.class))).thenReturn(playerPage);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(playerDTO);

        mockMvc.perform(get("/api/players")
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Test"));
    }

    @Test
    void getPlayers_withQuery_shouldReturnMatchingPlayers() throws Exception {
        String query = "test";
        Player player = new Player();
        TeamBaseDTO teamDTO = new TeamBaseDTO(UUID.randomUUID(), "Test Team", "Abbr");
        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Test Nation");
        PlayerListDTO playerDTO = new PlayerListDTO(UUID.randomUUID(), "Test", "Player", 25, 50.0, teamDTO, nationDTO);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "overallRating"));

        Page<Player> playerPage = new PageImpl<>(List.of(player), pageable, 1);

        when(playerService.findPlayersByFirstOrLastName(anyString(), any(Pageable.class))).thenReturn(playerPage);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(playerDTO);

        mockMvc.perform(get("/api/players")
                        .param("query", query)
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Test"));
    }

    @Test
    void getPlayerById_shouldReturnPlayerDetail() throws Exception {
        UUID id = UUID.randomUUID();
        Player player = new Player();
        PlayerDetailDTO playerDTO = new PlayerDetailDTO(id, "Test", "Player", null, 0, 0, 0.0, null, null, false, null, null, null, null, null, null);

        when(playerService.getPlayerById(id)).thenReturn(player);
        when(playerMapper.toDetailDTO(player)).thenReturn(playerDTO);

        mockMvc.perform(get("/api/players/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void getBestPlayersByAttribute_shouldReturnBestPlayers() throws Exception {
        Player player = new Player();
        PlayerListDTO playerDTO = new PlayerListDTO(UUID.randomUUID(), "Test", "Player", 85, "ST", 0.0);
        Map<String, List<Player>> bestPlayers = Map.of("pace", List.of(player));

        when(playerService.getBestPlayersByAttribute()).thenReturn(bestPlayers);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(playerDTO);

        mockMvc.perform(get("/api/players/best")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pace[0].firstName").value("Test"));
    }

    @Test
    void getSimilarPlayers_shouldReturnSimilarPlayers() throws Exception {
        UUID id = UUID.randomUUID();
        Player player = new Player();
        PlayerDetailDTO playerDTO = new PlayerDetailDTO(id, "Similar", "Player", null, 0, 0, 0.0, null, null, false, null, null, null, null, null, null);
        List<Player> similarPlayers = List.of(player);

        when(playerService.getSimilarPlayers(id)).thenReturn(similarPlayers);
        when(playerMapper.toDetailDTO(any(Player.class))).thenReturn(playerDTO);

        mockMvc.perform(get("/api/players/{id}/similar", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Similar"));
    }

    @Test
    void scoutingPlayers_shouldReturnScoutedPlayers() throws Exception {
        PlayerListDTO playerDTO = new PlayerListDTO(UUID.randomUUID(), "Scout", "Player", 80, "CDM", 10.0);
        List<PlayerListDTO> scoutedPlayers = List.of(playerDTO);

        when(playerService.playerScouting(any(Double.class), any(String.class), any(Integer.class))).thenReturn(scoutedPlayers);

        mockMvc.perform(get("/api/players/scouting")
                        .param("marketValueMax", "50.0")
                        .param("position", "CDM")
                        .param("minValue", "75")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Scout"));
    }

    @Test
    void getPlayersByIds_shouldReturnPlayersByIds() throws Exception {
        UUID playerId1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID playerId2 = UUID.fromString("00000000-0000-0000-0000-000000000002");

        Player player1 = new Player();
        player1.setId(playerId1);
        Player player2 = new Player();
        player2.setId(playerId2);

        PlayerDetailDTO dto1 = new PlayerDetailDTO(playerId1, "Player1", "Test", null, 0, 0, 0.0, null, null, false, null, null, null, null, null, null);
        PlayerDetailDTO dto2 = new PlayerDetailDTO(playerId2, "Player2", "Test", null, 0, 0, 0.0, null, null, false, null, null, null, null, null, null);

        when(playerService.getPlayersByIds(anyList())).thenReturn(List.of(player1, player2));

        when(playerMapper.toDetailDTO(player1)).thenReturn(dto1);
        when(playerMapper.toDetailDTO(player2)).thenReturn(dto2);

        mockMvc.perform(get("/api/players/compare")
                        .param("playerId", playerId1.toString())
                        .param("compareToPlayerId", playerId2.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Player1"))
                .andExpect(jsonPath("$[1].firstName").value("Player2"));
    }
}