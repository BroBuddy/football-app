package com.buddy.football.bootstrap.data;

import com.buddy.football.league.entity.League;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LeagueDataTest {

    private NationRepository nationRepository;
    private LeagueData leagueData;

    @BeforeEach
    void setUp() {
        nationRepository = Mockito.mock(NationRepository.class);

        Mockito.when(nationRepository.findByCode("DEU")).thenReturn(Optional.of(new Nation("Germany", "DEU")));
        Mockito.when(nationRepository.findByCode("ESP")).thenReturn(Optional.of(new Nation("Spain", "ESP")));
        Mockito.when(nationRepository.findByCode("ITA")).thenReturn(Optional.of(new Nation("Italy", "ITA")));
        Mockito.when(nationRepository.findByCode("ENG")).thenReturn(Optional.of(new Nation("England", "ENG")));
        Mockito.when(nationRepository.findByCode("FRA")).thenReturn(Optional.of(new Nation("France", "FRA")));

        leagueData = new LeagueData(nationRepository);
    }

    @Test
    void testGetLeagues() {
        List<League> leagues = leagueData.get();
        assertEquals(5, leagues.size());
        assertEquals("Bundesliga", leagues.get(0).getName());
        assertEquals("DEU", leagues.get(0).getNation().getCode());
    }

    @Test
    void testNationNotFoundThrows() {
        NationRepository mockRepo = Mockito.mock(NationRepository.class);
        Mockito.when(mockRepo.findByCode("DEU")).thenReturn(Optional.empty());
        LeagueData ld = new LeagueData(mockRepo);

        RuntimeException ex = assertThrows(RuntimeException.class, ld::get);
        assertTrue(ex.getMessage().contains("Nation nicht gefunden"));
    }
}
