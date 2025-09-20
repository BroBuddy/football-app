package com.buddy.football.player.service;

import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.dto.PlayerCalculations;
import com.buddy.football.player.dto.PlayerDataDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.calculator.PlayerAttributeCalculator;
import com.buddy.football.player.calculator.PlayerAttributeWeights;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.util.metric.MetricCalculator;
import com.buddy.football.player.calculator.PlayerPositionCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerBuildingService {

    private final PlayerPositionCalculator playerPositionCalculator;
    private final PlayerAttributeCalculator playerAttributeCalculator;
    private final MetricCalculator metricCalculator;

    public PlayerBuildingService(PlayerPositionCalculator playerPositionCalculator, PlayerAttributeCalculator playerAttributeCalculator, MetricCalculator metricCalculator) {
        this.playerPositionCalculator = playerPositionCalculator;
        this.playerAttributeCalculator = playerAttributeCalculator;
        this.metricCalculator = metricCalculator;
    }

    public PlayerAttributes buildPlayerAttributes(PlayerDataDTO raw, Player player) {
        PlayerAttributes attributes = new PlayerAttributes();
        attributes.setPlayer(player);
        attributes.setAttackingAttributes(raw.attackingAttributes());
        attributes.setSkillAttributes(raw.skillAttributes());
        attributes.setMovementAttributes(raw.movementAttributes());
        attributes.setPowerAttributes(raw.powerAttributes());
        attributes.setMentalityAttributes(raw.mentalityAttributes());
        attributes.setDefendingAttributes(raw.defendingAttributes());
        attributes.setGoalkeepingAttributes(raw.goalkeepingAttributes());

        return attributes;
    }

    public PlayerPositions buildPlayerPositions(Player player) {
        Map<String, Integer> ratings = playerPositionCalculator.calculatePositionRatings(
                playerAttributeCalculator.extractAttributes(player.getAttributes())
        );

        PlayerPositions positions = new PlayerPositions();
        positions.setPlayer(player);

        ratings.forEach((k,v) -> {
            switch(k.toLowerCase()) {
                case "st" -> positions.setSt(v);
                case "cam" -> positions.setCam(v);
                case "cb" -> positions.setCb(v);
                case "cm" -> positions.setCm(v);
                case "rw" -> positions.setRw(v);
                case "rm" -> positions.setRm(v);
                case "lw" -> positions.setLw(v);
                case "lm" -> positions.setLm(v);
                case "rb" -> positions.setRb(v);
                case "lb" -> positions.setLb(v);
                case "cdm" -> positions.setCdm(v);
                case "gk" -> positions.setGk(v);
            }
        });

        positions.setCreated(LocalDateTime.now());
        positions.setUpdated(LocalDateTime.now());

        return positions;
    }

    public String getMainPositionsAsString(PlayerPositions positions) {
        return getPositionMap(positions).entrySet().stream()
                .filter(e -> e.getValue() == calculateOverallRating(positions))
                .map(Map.Entry::getKey)
                .limit(3)
                .collect(Collectors.joining(","));
    }

    private Map<String, Integer> getPositionMap(PlayerPositions positions) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("lm", positions.getLm());
        map.put("rw", positions.getRw());
        map.put("rm", positions.getRm());
        map.put("lw", positions.getLw());
        map.put("rb", positions.getRb());
        map.put("lb", positions.getLb());
        map.put("cm", positions.getCm());
        map.put("cb", positions.getCb());
        map.put("cdm", positions.getCdm());
        map.put("cam", positions.getCam());
        map.put("st", positions.getSt());
        map.put("gk", positions.getGk());

        return map;
    }

    public int calculateOverallRating(PlayerPositions positions) {
        return getPositionMap(positions).values().stream()
                .filter(Objects::nonNull)
                .mapToInt(i -> i)
                .max()
                .orElse(10);
    }

    public PlayerMainAttributes buildMainAttributes(PlayerAttributes attributes) {
        Map<String, Integer> mainMap = Arrays.stream(PlayerAttributeWeights.AttributeCategory.values())
                .collect(Collectors.toMap(
                        c -> c.name().toLowerCase(),
                        c -> playerAttributeCalculator.calculateAttribute(c, attributes)
                ));

        PlayerMainAttributes main = new PlayerMainAttributes();
        main.setDribbling(mainMap.getOrDefault("dribbling", 0));
        main.setPace(mainMap.getOrDefault("pace", 0));
        main.setShooting(mainMap.getOrDefault("shooting", 0));
        main.setDefending(mainMap.getOrDefault("defending", 0));
        main.setGoalkeeping(mainMap.getOrDefault("goalkeeping", 0));
        main.setPassing(mainMap.getOrDefault("passing", 0));
        main.setPhysical(mainMap.getOrDefault("physical", 0));

        return main;
    }

    public PlayerCalculations buildPlayerCalculations(PlayerAttributes attributes) {
        Map<String, Double> metrics = new LinkedHashMap<>();
        metrics.put("xG", metricCalculator.calculateXG(attributes));
        metrics.put("xA", metricCalculator.calculateXA(attributes));
        metrics.put("xT", metricCalculator.calculateXT(attributes));
        metrics.put("successfulDribbles", metricCalculator.calculateSuccessfulDribbles(attributes));
        metrics.put("progressivePasses", metricCalculator.calculateProgressivePasses(attributes));
        metrics.put("tackleSuccess", metricCalculator.calculateTackleSuccess(attributes));
        metrics.put("interceptions", metricCalculator.calculateInterceptions(attributes));
        metrics.put("clearances", metricCalculator.calculateClearances(attributes));
        metrics.put("pressureRecovery", metricCalculator.calculatePressureRecovery(attributes));

        return new PlayerCalculations(metrics);
    }
}
