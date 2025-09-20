package com.buddy.football.player.service;

import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPlayersPaged() {
        Player player = new Player();
        Page<Player> page = new PageImpl<>(List.of(player));

        when(playerRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<Player> result = playerService.getAllPlayers(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }


    @Test
    void shouldReturnPlayerById() {
        UUID id = UUID.randomUUID();
        Player player = new Player();
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));

        Player result = playerService.getPlayerById(id);

        assertNotNull(result);
        assertEquals(player, result);
    }

    @Test
    void shouldThrowExceptionWhenPlayerNotFound() {
        UUID id = UUID.randomUUID();
        when(playerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.getPlayerById(id));
    }

    @Test
    void shouldReturnPlayersByIds() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Player p1 = new Player();
        Player p2 = new Player();
        when(playerRepository.findAllById(List.of(id1, id2))).thenReturn(List.of(p1, p2));

        List<Player> result = playerService.getPlayersByIds(List.of(id1, id2));

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
    }

    @Test
    void shouldFindPlayersByFirstOrLastName() {
        Player player = new Player();

        Page<Player> playerPage = new PageImpl<>(List.of(player));

        when(playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                ArgumentMatchers.eq("Smith"),
                ArgumentMatchers.eq("Smith"),
                ArgumentMatchers.any(Pageable.class)
        )).thenReturn(playerPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> result = playerService.findPlayersByFirstOrLastName("Smith", pageable);

        assertEquals(1, result.getTotalElements());
        assertTrue(result.getContent().contains(player));
    }
}
