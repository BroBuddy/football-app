package com.buddy.football.player.service;

import com.buddy.football.league.entity.League;
import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.dto.PlayerListDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import com.buddy.football.team.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private UUID playerId;
    private UUID leagueId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        leagueId = UUID.randomUUID();
        player = new Player();
        player.setId(playerId);
        player.setOverallRating(85);
        player.setMainPositions("st");
        Team team = new Team();
        League league = new League();
        league.setId(leagueId);
        team.setLeague(league);
        player.setTeam(team);
    }

    @Test
    void getAllPlayers_shouldReturnPlayerPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> playerPage = new PageImpl<>(List.of(player), pageable, 1);
        when(playerRepository.findAll(pageable)).thenReturn(playerPage);

        // Act
        Page<Player> result = playerService.getAllPlayers(pageable);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(playerRepository).findAll(pageable);
    }

    @Test
    void getPlayersByIds_shouldReturnPlayers() {
        // Arrange
        List<UUID> ids = List.of(playerId);
        when(playerRepository.findAllById(ids)).thenReturn(List.of(player));

        // Act
        List<Player> result = playerService.getPlayersByIds(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(playerId, result.get(0).getId());
        verify(playerRepository).findAllById(ids);
    }

    @Test
    void findPlayersByFirstOrLastName_shouldReturnFilteredPage() {
        // Arrange
        String query = "test";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> playerPage = new PageImpl<>(List.of(player), pageable, 1);
        when(playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable)).thenReturn(playerPage);

        // Act
        Page<Player> result = playerService.findPlayersByFirstOrLastName(query, pageable);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(playerRepository).findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);
    }

    @Test
    void getBestPlayersByAttribute_shouldReturnMapOfBestPlayers() {
        // Arrange
        Player striker = new Player();
        striker.setMainAttributes(new PlayerMainAttributes());
        striker.getMainAttributes().setShooting(95);

        when(playerRepository.findTop5ByOrderByMainAttributes_DribblingDesc()).thenReturn(Collections.emptyList());
        when(playerRepository.findTop5ByOrderByMainAttributes_ShootingDesc()).thenReturn(List.of(striker));
        when(playerRepository.findTop5ByOrderByMainAttributes_PassingDesc()).thenReturn(Collections.emptyList());
        when(playerRepository.findTop5ByOrderByMainAttributes_PaceDesc()).thenReturn(Collections.emptyList());
        when(playerRepository.findTop5ByOrderByMainAttributes_PhysicalDesc()).thenReturn(Collections.emptyList());
        when(playerRepository.findTop5ByOrderByMainAttributes_DefendingDesc()).thenReturn(Collections.emptyList());
        when(playerRepository.findTop5ByOrderByMainAttributes_GoalkeepingDesc()).thenReturn(Collections.emptyList());

        // Act
        Map<String, List<Player>> result = playerService.getBestPlayersByAttribute();

        // Assert
        assertNotNull(result);
        assertEquals(7, result.size());
        assertEquals(1, result.get("shooting").size());
        assertEquals(95, result.get("shooting").get(0).getMainAttributes().getShooting());
    }

    @Test
    void getSimilarPlayers_shouldReturnPlayersFromSameLeague() {
        // Arrange
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Corrected: Return a list with 5 players to prevent the second repository call
        List<Player> players = Arrays.asList(new Player(), new Player(), new Player(), new Player(), new Player());
        when(playerRepository.findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_Id(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        )).thenReturn(new ArrayList<>(players));

        // Act
        List<Player> result = playerService.getSimilarPlayers(playerId);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        verify(playerRepository).findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_Id(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        );
        // This verify now passes because the mock returns 5 players, so the second query is not executed.
        verify(playerRepository, never()).findPlayersByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_IdIsNot(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        );
    }

    @Test
    void getSimilarPlayers_shouldReturnPlayersFromOtherLeagues_ifSameLeagueSearchFails() {
        // Arrange
        Player otherPlayer = new Player();
        otherPlayer.setId(UUID.randomUUID());

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_Id(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        )).thenReturn(new ArrayList<>());
        when(playerRepository.findPlayersByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_IdIsNot(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        )).thenReturn(List.of(otherPlayer));

        // Act
        List<Player> result = playerService.getSimilarPlayers(playerId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(playerRepository).findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_Id(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        );
        verify(playerRepository).findPlayersByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_IdIsNot(
                anyString(), anyInt(), anyInt(), any(UUID.class), any(UUID.class)
        );
    }

    @Test
    void playerScouting_shouldReturnScoutedPlayers() {
        // Arrange
        double marketValueMax = 50.0;
        String position = "st";
        int minValue = 80;
        Pageable pageable = PageRequest.of(0, 15);
        Page<Player> playerPage = new PageImpl<>(List.of(player), pageable, 1);
        PlayerListDTO playerDTO = new PlayerListDTO(UUID.randomUUID(), "Scout", "Player", 25, 45.0, null, null);

        when(playerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(playerPage);
        when(playerMapper.toListDTO(any(Player.class))).thenReturn(playerDTO);

        // Act
        List<PlayerListDTO> result = playerService.playerScouting(marketValueMax, position, minValue);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(playerRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(playerMapper).toListDTO(any(Player.class));
    }

    @Test
    void playerScouting_withInvalidPosition_shouldThrowException() {
        // Arrange
        double marketValueMax = 50.0;
        String invalidPosition = "invalid";
        int minValue = 80;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                playerService.playerScouting(marketValueMax, invalidPosition, minValue));
        verify(playerRepository, never()).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void getPlayerById_shouldReturnPlayer() {
        // Arrange
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Act
        Player result = playerService.getPlayerById(playerId);

        // Assert
        assertNotNull(result);
        assertEquals(playerId, result.getId());
        verify(playerRepository).findById(playerId);
    }

    @Test
    void getPlayerById_withInvalidId_shouldThrowException() {
        // Arrange
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> playerService.getPlayerById(playerId));
        verify(playerRepository).findById(playerId);
    }
}