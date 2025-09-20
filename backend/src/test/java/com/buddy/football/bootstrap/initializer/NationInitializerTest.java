package com.buddy.football.bootstrap.initializer;

import com.buddy.football.nation.repository.NationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class NationInitializerTest {

    private NationRepository nationRepository;
    private NationInitializer nationInitializer;

    @BeforeEach
    void setup() {
        nationRepository = Mockito.mock(NationRepository.class);
        nationInitializer = new NationInitializer(nationRepository);
    }

    @Test
    void testRun_initializesNationsWhenEmpty() throws Exception {
        when(nationRepository.count()).thenReturn(0L);

        nationInitializer.run();

        verify(nationRepository, times(1)).saveAll(any(List.class));
    }

    @Test
    void testRun_skipsInitializationWhenNotEmpty() throws Exception {
        when(nationRepository.count()).thenReturn(5L);

        nationInitializer.run();

        verify(nationRepository, never()).saveAll(any());
    }
}
