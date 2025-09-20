package com.buddy.football.simmulation.service;

import com.buddy.football.player.dto.PlayerMatchDTO;
import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.entity.TeamStats;
import com.buddy.football.simulation.service.MatchService;
import com.buddy.football.util.match.MatchWeights;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    private static MatchLineupDTO homeLineup;
    private static MatchLineupDTO awayLineup;

    @BeforeAll
    static void setup() {
        PlayerMatchDTO homePlayer = Mockito.mock(PlayerMatchDTO.class);
        Mockito.when(homePlayer.metrics()).thenReturn(Map.of(
                "successfulDribbles", 10.0,
                "xA", 5.0,
                "progressivePasses", 8.0,
                "xT", 3.0,
                "xG", 2.0,
                "interceptions", 1.0,
                "tackles", 2.0,
                "blocks", 1.0,
                "clearances", 0.5
        ));

        PlayerMatchDTO awayPlayer = Mockito.mock(PlayerMatchDTO.class);
        Mockito.when(awayPlayer.metrics()).thenReturn(Map.of(
                "successfulDribbles", 5.0,
                "xA", 2.0,
                "progressivePasses", 3.0,
                "xT", 1.0,
                "xG", 1.0,
                "interceptions", 0.5,
                "tackles", 1.0,
                "blocks", 0.5,
                "clearances", 0.2
        ));

        homeLineup = new MatchLineupDTO("Home", new ArrayList<>(List.of(homePlayer)));
        awayLineup = new MatchLineupDTO("Away", new ArrayList<>(List.of(awayPlayer)));

        Map<String, Double> mutableConfig = new HashMap<>(MatchWeights.CONFIG);
        mutableConfig.putIfAbsent("offense.xG", 1.0);
        mutableConfig.putIfAbsent("offense.xA", 1.0);
        mutableConfig.putIfAbsent("offense.xT", 1.0);
        mutableConfig.putIfAbsent("offense.successfulDribbles", 1.0);
        mutableConfig.putIfAbsent("offense.progressivePasses", 1.0);
        mutableConfig.putIfAbsent("offense.push", 1.0);
        mutableConfig.putIfAbsent("offense.factor", 1.0);
        mutableConfig.putIfAbsent("defense.tackles", 1.0);
        mutableConfig.putIfAbsent("defense.interceptions", 1.0);
        mutableConfig.putIfAbsent("defense.clearances", 1.0);
        mutableConfig.putIfAbsent("defense.blocks", 1.0);
        mutableConfig.putIfAbsent("defense.push", 1.0);
        mutableConfig.putIfAbsent("defense.factor", 1.0);
        mutableConfig.putIfAbsent("goals.min", 0.0);
        mutableConfig.putIfAbsent("goals.max", 10.0);
    }

    @Test
    void testSimulateMatch_returnsResult() {
        MatchTeamDTO match = new MatchTeamDTO(homeLineup, awayLineup);
        MatchResultDTO result = MatchService.simulateMatch(match);

        assertNotNull(result);
        assertEquals("Home", result.homeTeam());
        assertEquals("Away", result.awayTeam());
        assertTrue(result.homeGoals() >= 0 && result.homeGoals() <= 10);
        assertTrue(result.awayGoals() >= 0 && result.awayGoals() <= 10);
    }

    @Test
    void testCalculateTeamStats_sumsMetricsCorrectly() throws Exception {
        var method = MatchService.class.getDeclaredMethod("calculateTeamStats", MatchLineupDTO.class);
        method.setAccessible(true);

        TeamStats stats = (TeamStats) method.invoke(null, homeLineup);

        assertEquals(10.0, stats.successfulDribbles);
        assertEquals(5.0, stats.xA);
        assertEquals(8.0, stats.progressivePasses);
        assertEquals(3.0, stats.xT);
        assertEquals(2.0, stats.xG);
        assertEquals(1.0, stats.interceptions);
        assertEquals(2.0, stats.tackles);
        assertEquals(1.0, stats.blocks);
        assertEquals(0.5, stats.clearances);
    }

    @Test
    void testApplyHomeAdvantage_multipliesStats() throws Exception {
        var method = MatchService.class.getDeclaredMethod("applyHomeAdvantage", TeamStats.class);
        method.setAccessible(true);

        TeamStats stats = new TeamStats();
        stats.successfulDribbles = 10;
        stats.xA = 5;

        method.invoke(null, stats);

        assertEquals(11.0, stats.successfulDribbles, 0.01);
        assertEquals(5.5, stats.xA, 0.01);
    }
}
