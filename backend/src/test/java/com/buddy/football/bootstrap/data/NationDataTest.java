package com.buddy.football.bootstrap.data;

import com.buddy.football.nation.entity.Nation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NationDataTest {

    @Test
    void testGetAllNations() {
        List<Nation> nations = NationData.get();

        assertNotNull(nations);
        assertFalse(nations.isEmpty());

        assertTrue(nations.stream().anyMatch(n -> n.getCode().equals("DEU"))); // Germany
        assertTrue(nations.stream().anyMatch(n -> n.getCode().equals("BRA"))); // Brazil
        assertTrue(nations.stream().anyMatch(n -> n.getCode().equals("AUS"))); // Australia

        nations.forEach(n -> assertNotNull(n.getFlagUrl()));
    }
}
