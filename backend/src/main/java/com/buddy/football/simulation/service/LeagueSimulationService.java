package com.buddy.football.simulation.service;

import com.buddy.football.simulation.entity.TeamResult;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.dto.MatchResultTuple;
import com.buddy.football.team.dto.TeamListDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LeagueSimulationService {

    private final MatchSimulationService matchSimulationService;

    public LeagueSimulationService(MatchSimulationService matchSimulationService) {
        this.matchSimulationService = matchSimulationService;
    }

    public List<Map<String, Object>> simulateLeague(List<TeamListDTO> teams) {
        AtomicInteger rank = new AtomicInteger(1);

        Map<UUID, TeamResult> statsMap = teams.stream()
                .collect(Collectors.toMap(TeamListDTO::id, t -> new TeamResult(t.id(), t.name())));

        List<MatchResultTuple> matchResults = generateMatchResults(teams);

        for (MatchResultTuple r : matchResults) {
            TeamResult homeStats = statsMap.get(r.homeId());
            TeamResult awayStats = statsMap.get(r.awayId());

            setTeamProperties(
                    homeStats,
                    awayStats,
                    new MatchResultDTO(
                            homeStats.name,
                            r.homeGoals(),
                            awayStats.name,
                            r.awayGoals()
                    )
            );
        }

        return buildTeamValues(statsMap, rank);
    }

    private List<MatchResultTuple> generateMatchResults(List<TeamListDTO> teams) {
        List<MatchResultTuple> matchResults = IntStream.range(0, teams.size())
                .boxed()
                .flatMap(i -> IntStream.range(0, teams.size())
                        .filter(j -> i != j)
                        .mapToObj(j -> new AbstractMap.SimpleEntry<>(i, j)))
                .map(entry -> {
                    int i = entry.getKey();
                    int j = entry.getValue();
                    TeamListDTO home = teams.get(i);
                    TeamListDTO away = teams.get(j);

                    MatchResultDTO result = matchSimulationService.simulateMatch(home.id(), away.id());
                    return new MatchResultTuple(home.id(), away.id(), result.homeGoals(), result.awayGoals());
                })
                .toList();
        return matchResults;
    }

    private static void setTeamProperties(TeamResult homeStats, TeamResult awayStats, MatchResultDTO result) {
        homeStats.games++;
        awayStats.games++;

        homeStats.goalsFor += result.homeGoals();
        homeStats.goalsAgainst += result.awayGoals();
        awayStats.goalsFor += result.awayGoals();
        awayStats.goalsAgainst += result.homeGoals();

        if (result.homeGoals() > result.awayGoals()) {
            homeStats.won++;
            homeStats.points += 3;
            awayStats.lose++;
        } else if (result.homeGoals() < result.awayGoals()) {
            awayStats.won++;
            awayStats.points += 3;
            homeStats.lose++;
        } else {
            homeStats.draw++;
            awayStats.draw++;
            homeStats.points++;
            awayStats.points++;
        }
    }

    private static List<Map<String, Object>> buildTeamValues(Map<UUID, TeamResult> statsMap, AtomicInteger rank) {
        return statsMap.values().stream()
                .sorted(
                    Comparator.comparingInt(TeamResult::getPoints).reversed()
                    .thenComparing(Comparator.comparingInt(TeamResult::getDiff).reversed())
                    .thenComparing(Comparator.comparingInt(TeamResult::getGoalsFor).reversed())
                )
                .map(ts -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("rank", rank.getAndIncrement());
                    map.put("id", ts.getId());
                    map.put("name", ts.getName());
                    map.put("games", ts.getGames());
                    map.put("won", ts.getWon());
                    map.put("draw", ts.getDraw());
                    map.put("lose", ts.getLose());
                    map.put("goals", ts.getGoalsFor() + ":" + ts.getGoalsAgainst());
                    map.put("diff", ts.getDiff());
                    map.put("points", ts.getPoints());
                    return map;
                })
                .toList();
    }

}
