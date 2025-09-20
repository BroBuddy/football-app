package com.buddy.football.simmulation.dto;

import com.buddy.football.player.dto.PlayerMatchDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchMapperTest {

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private MatchMapper matchMapper;

    private Team team;
    private Player startingPlayer;
    private Player nonStartingPlayer;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Test Team");
        team.setLogoUrl("logo.png");

        startingPlayer = new Player();
        startingPlayer.setStarting(true);
        startingPlayer.setMarketValue(50.0);

        nonStartingPlayer = new Player();
        nonStartingPlayer.setStarting(false);
        nonStartingPlayer.setMarketValue(20.0);

        team.setPlayers(List.of(startingPlayer, nonStartingPlayer));
    }

    @Test
    void shouldMapTeamToLineupDTOAndFilterStartingPlayers() {
        PlayerMatchDTO startingPlayerDto = new PlayerMatchDTO(
                startingPlayer.getId(),
                "Starter",
                "Player",
                true,
                startingPlayer.getMarketValue(),
                null
        );

        when(playerMapper.toMatchDTO(startingPlayer))
                .thenReturn(startingPlayerDto);

        MatchLineupDTO lineupDTO = matchMapper.toLineupDTO(team);

        assertNotNull(lineupDTO);
        assertEquals(team.getId(), lineupDTO.id());
        assertEquals(team.getName(), lineupDTO.name());
        assertEquals(team.getLogoUrl(), lineupDTO.logoUrl());

        assertEquals(team.getMarketValue(), lineupDTO.marketValue());

        List<PlayerMatchDTO> startingPlayers = lineupDTO.startingPlayers();
        assertNotNull(startingPlayers);
        assertEquals(1, startingPlayers.size());
        assertEquals("Starter", startingPlayers.get(0).firstName());
    }

    @Test
    void shouldReturnEmptyStartingPlayersListIfNoPlayersAreStarting() {
        startingPlayer.setStarting(false);
        nonStartingPlayer.setStarting(false);
        team.setPlayers(List.of(startingPlayer, nonStartingPlayer));

        MatchLineupDTO lineupDTO = matchMapper.toLineupDTO(team);

        assertNotNull(lineupDTO);
        assertNotNull(lineupDTO.startingPlayers());
        assertEquals(0, lineupDTO.startingPlayers().size());
    }

    @Test
    void shouldReturnEmptyStartingPlayersListIfTeamHasNoPlayers() {
        team.setPlayers(List.of());

        MatchLineupDTO lineupDTO = matchMapper.toLineupDTO(team);

        assertNotNull(lineupDTO);
        assertNotNull(lineupDTO.startingPlayers());
        assertEquals(0, lineupDTO.startingPlayers().size());
    }
}