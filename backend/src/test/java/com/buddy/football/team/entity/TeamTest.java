package com.buddy.football.team.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void shouldTestEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        Team team1 = new Team();
        team1.setId(id);
        team1.setName("Bayer 04 Leverkusen");

        Team team2 = new Team();
        team2.setId(id);
        team2.setName("FC Bayern München"); // Different name

        assertEquals(team1, team2);

        assertEquals(team1.hashCode(), team2.hashCode());
    }

    @Test
    void shouldTestToString() {
        Team team = new Team();
        team.setName("Bayer 04 Leverkusen");
        team.setLogoUrl("leverkusen.png");

        String str = team.toString();
        assertTrue(str.contains("id=null"));
        assertTrue(str.contains("name='Bayer 04 Leverkusen'"));
        assertTrue(str.contains("logoUrl='leverkusen.png'"));
    }
}
