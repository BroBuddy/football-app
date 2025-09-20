package com.buddy.football.bootstrap.initializer;

import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import com.buddy.football.player.service.PlayerImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerInitializerTest {

    private PlayerImportService importService;
    private PlayerRepository playerRepository;
    private PlayerInitializer playerInitializer;

    @BeforeEach
    void setup() {
        importService = Mockito.mock(PlayerImportService.class);
        playerRepository = Mockito.mock(PlayerRepository.class);
        playerInitializer = new PlayerInitializer(importService, playerRepository);
    }

    @Test
    void testRun_initializesPlayersWhenEmpty() throws Exception {
        when(playerRepository.count()).thenReturn(0L);

        when(importService.importPlayers(any(InputStream.class)))
                .thenReturn(List.of(new Player()), List.of(new Player()), List.of(new Player()), List.of(new Player()));

        playerInitializer.run();

        verify(playerRepository, times(1)).saveAll(any(List.class));
        verify(importService, times(5)).importPlayers(any(InputStream.class));
    }

    @Test
    void testRun_skipsInitializationWhenNotEmpty() throws Exception {
        when(playerRepository.count()).thenReturn(5L);

        playerInitializer.run();

        verify(playerRepository, never()).saveAll(any());
        verify(importService, never()).importPlayers(any());
    }
}
