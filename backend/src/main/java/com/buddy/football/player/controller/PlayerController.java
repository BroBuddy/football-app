package com.buddy.football.player.controller;

import com.buddy.football.player.dto.PlayerDetailDTO;
import com.buddy.football.player.dto.PlayerListDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(
            PlayerService playerService,
            PlayerMapper playerMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PlayerListDTO>> getPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "overallRating"));

        Page<Player> players;

        if (query != null && !query.isEmpty()) {
            players = playerService.findPlayersByFirstOrLastName(query, pageable);
        } else {
            players = playerService.getAllPlayers(pageable);
        }

        Page<PlayerListDTO> playerDTOs = players.map(playerMapper::toListDTO);

        return ResponseEntity.ok(playerDTOs);
    }

    @GetMapping("/{id}")
    public PlayerDetailDTO getPlayerById(@PathVariable UUID id) {
        return playerMapper.toDetailDTO(playerService.getPlayerById(id));
    }

    @GetMapping("/best")
    public ResponseEntity<Map<String, List<PlayerListDTO>>> getBestPlayersByAttribute() {
        Map<String, List<Player>> bestPlayers = playerService.getBestPlayersByAttribute();

        Map<String, List<PlayerListDTO>> bestPlayersDTOs = bestPlayers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(playerMapper::toListDTO)
                                .toList()
                ));

        return ResponseEntity.ok(bestPlayersDTOs);
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<PlayerDetailDTO>> getSimilarPlayers(@PathVariable UUID id) {
        List<Player> similarPlayers = playerService.getSimilarPlayers(id);

        List<PlayerDetailDTO> similarPlayersDTOs = similarPlayers.stream()
                .map(playerMapper::toDetailDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(similarPlayersDTOs);
    }

    @GetMapping("/scouting")
    public List<PlayerListDTO> scoutingPlayers(
            @RequestParam double marketValueMax,
            @RequestParam String position,
            @RequestParam int minValue) {

        return playerService.playerScouting(marketValueMax, position, minValue);
    }

    @GetMapping("/compare")
    public List<PlayerDetailDTO> getPlayersByIds(@RequestParam UUID playerId, @RequestParam UUID compareToPlayerId) {
        return playerService.getPlayersByIds(List.of(playerId, compareToPlayerId)).stream()
                .sorted(Comparator.comparing(Player::getId))
                .map(playerMapper::toDetailDTO)
                .toList();
    }

    private List<PlayerDetailDTO> findSimilarPlayers(List<Player> allPlayers, PlayerDetailDTO player, Set<String> mainPositions, Integer overall) {
        return allPlayers.stream()
                .filter(p -> !p.getId().equals(player.id()))
                .filter(p -> p.getMainPositions() != null && !Collections.disjoint(
                        Arrays.stream(p.getMainPositions().split(",")).map(String::trim).collect(Collectors.toSet()),
                        mainPositions
                ))
                .filter(p -> overall != null && p.getOverallRating() != null
                        && Math.abs(p.getOverallRating() - overall) <= 2)
                .sorted(Comparator.comparing(Player::getOverallRating, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .map(playerMapper::toDetailDTO)
                .toList();
    }
}
