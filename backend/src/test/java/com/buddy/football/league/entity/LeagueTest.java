package com.buddy.football.league.entity;

import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTest {

    @Test
    void shouldTestConstructorsAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        League league = new League("Premier League", null, List.of(), now, now);

        assertEquals("Premier League", league.getName());
        assertEquals(now, league.getCreated());
        assertEquals(now, league.getUpdated());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        League l1 = new League("Premier League", null, List.of(), now, now);
        l1.setId(id);

        League l2 = new League("Premier League", null, List.of(), now, now);
        l2.setId(id);

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    void shouldTestToString() {
        League league = new League();
        league.setName("Premier League");

        String str = league.toString();
        assertTrue(str.contains("Premier League"));
    }

    @Test
    void shouldSortTeamsByMarketValue() {
        Team t1 = new Team();
        t1.setName("Team 1");
        t1.setPlayers(List.of());

        Team t2 = new Team();
        t2.setName("Team 2");
        t2.setPlayers(List.of());

        League league = new League();
        league.setTeams(List.of(t2, t1));

        List<Team> sorted = league.getTeamsSortedByMarketValue();
        assertEquals(2, sorted.size());
        assertEquals(t2, sorted.get(0));
    }
}
