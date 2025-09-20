package com.buddy.football.bootstrap.initializer;

import com.buddy.football.league.repository.LeagueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class LeagueInitializerTest {

    private LeagueRepository leagueRepository;
    private LeagueInitializer leagueInitializer;

    @BeforeEach
    void setup() {
        leagueRepository = Mockito.mock(LeagueRepository.class);
        leagueInitializer = new LeagueInitializer(leagueRepository);
    }

    @Test
    void testRun_initializesLeaguesWhenEmpty() throws Exception {
        when(leagueRepository.count()).thenReturn(0L);

        leagueInitializer.run();

        verify(leagueRepository, times(1)).saveAll(any(List.class));
    }

    @Test
    void testRun_skipsInitializationWhenNotEmpty() throws Exception {
        when(leagueRepository.count()).thenReturn(5L);

        leagueInitializer.run();

        verify(leagueRepository, never()).saveAll(any());
    }
}
