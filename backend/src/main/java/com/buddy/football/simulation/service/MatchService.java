package com.buddy.football.simulation.service;

import com.buddy.football.player.dto.PlayerMatchDTO;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.entity.TeamStats;
import com.buddy.football.simulation.dto.MatchLineupDTO;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.util.match.MatchWeights;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
public class MatchService {

    private static final double HOME_ADVANTAGE = 0.10;

    public static MatchResultDTO simulateMatch(MatchTeamDTO match) {
        TeamStats homeStats = calculateTeamStats(match.home());
        TeamStats awayStats = calculateTeamStats(match.away());

        applyHomeAdvantage(homeStats);

        int homeGoals = simulateGoals(homeStats, awayStats);
        int awayGoals = simulateGoals(awayStats, homeStats);

        return new MatchResultDTO(match.home().name(), homeGoals,
                match.away().name(), awayGoals);
    }

    private static TeamStats calculateTeamStats(MatchLineupDTO lineup) {
        TeamStats stats = new TeamStats();

        for (PlayerMatchDTO p : lineup.startingPlayers()) {
            Map<String, Double> m = p.metrics();

            stats.successfulDribbles += m.getOrDefault("successfulDribbles", 0.0);
            stats.xA += m.getOrDefault("xA", 0.0);
            stats.progressivePasses += m.getOrDefault("progressivePasses", 0.0);
            stats.xT += m.getOrDefault("xT", 0.0);
            stats.xG += m.getOrDefault("xG", 0.0);

            stats.interceptions += m.getOrDefault("interceptions", 0.0);
            stats.tackles += m.getOrDefault("tackles", 0.0);
            stats.blocks += m.getOrDefault("blocks", 0.0);
            stats.clearances += m.getOrDefault("clearances", 0.0);
        }

        return stats;
    }

    private static void applyHomeAdvantage(TeamStats stats) {
        stats.successfulDribbles *= (1 + HOME_ADVANTAGE);
        stats.xA *= (1 + HOME_ADVANTAGE);
        stats.progressivePasses *= (1 + HOME_ADVANTAGE);
        stats.xT *= (1 + HOME_ADVANTAGE);
        stats.xG *= (1 + HOME_ADVANTAGE);
        stats.interceptions *= (1 + HOME_ADVANTAGE);
        stats.tackles *= (1 + HOME_ADVANTAGE);
        stats.blocks *= (1 + HOME_ADVANTAGE);
        stats.clearances *= (1 + HOME_ADVANTAGE);
    }

    private static int simulateGoals(TeamStats offense, TeamStats defense) {
        Random rand = new Random();

        double expectedGoals = calculateExpectedGoals(offense, defense);

        int goals = 0;
        int chances = (int) Math.round(expectedGoals * 4);

        for (int i = 0; i < chances; i++) {
            double conversionRate = 0.25 + Math.min(0.15, (offense.xG + offense.xT) / 200.0);

            if (rand.nextDouble() < conversionRate) {
                goals++;
            }
        }

        return goals;
    }

    private static double calculateExpectedGoals(TeamStats offense, TeamStats defense) {
        double offensivePower = Math.pow(
                    offense.xG * MatchWeights.CONFIG.get("offense.xG")
                    + offense.xA * MatchWeights.CONFIG.get("offense.xA")
                    + offense.xT * MatchWeights.CONFIG.get("offense.xT")
                    + offense.successfulDribbles * MatchWeights.CONFIG.get("offense.successfulDribbles")
                    + offense.progressivePasses * MatchWeights.CONFIG.get("offense.progressivePasses"),
                        MatchWeights.CONFIG.get("offense.push"));

        double defensivePower = Math.pow(
                    (defense.tackles * MatchWeights.CONFIG.get("defense.tackles"))
                    + (defense.interceptions * MatchWeights.CONFIG.get("defense.interceptions"))
                    + (defense.clearances * MatchWeights.CONFIG.get("defense.clearances"))
                    + (defense.blocks * MatchWeights.CONFIG.get("defense.blocks")),
                        MatchWeights.CONFIG.get("defense.push"));

        double expectedGoals = offensivePower / MatchWeights.CONFIG.get("offense.factor");

        double defenseFactor = 1.0 / (1.0 + defensivePower / MatchWeights.CONFIG.get("defense.factor"));
        expectedGoals *= defenseFactor;

        double variation = 0.95 + Math.random() * 0.1;
        expectedGoals *= variation;

        expectedGoals = Math.max(
                MatchWeights.CONFIG.get("goals.min"),
                Math.min(expectedGoals, MatchWeights.CONFIG.get("goals.max")));

        return expectedGoals;
    }


}
