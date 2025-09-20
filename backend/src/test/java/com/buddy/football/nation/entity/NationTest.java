package com.buddy.football.nation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NationTest {

    @Test
    void shouldTestConstructorsAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Nation nation = new Nation("Germany", "DEU", "flag.png", now, now);

        assertEquals("Germany", nation.getName());
        assertEquals("DEU", nation.getCode());
        assertEquals("flag.png", nation.getFlagUrl());
        assertEquals(now, nation.getCreated());
        assertEquals(now, nation.getUpdated());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Nation n1 = new Nation("Germany", "DEU", "flag.png", now, now);
        n1.setId(id);

        Nation n2 = new Nation("Germany", "DEU", "flag.png", now, now);
        n2.setId(id);

        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void shouldTestToString() {
        Nation nation = new Nation();
        nation.setName("Germany");
        nation.setCode("DEU");
        nation.setFlagUrl("flag.png");

        String str = nation.toString();
        assertTrue(str.contains("Germany"));
        assertTrue(str.contains("DEU"));
        assertTrue(str.contains("flag.png"));
    }

    @Test
    void shouldTestPreUpdate() {
        Nation nation = new Nation();
        nation.preUpdate();

        assertNotNull(nation.getCreated());
        assertNotNull(nation.getUpdated());
        assertEquals(nation.getCreated(), nation.getUpdated());
    }
}
