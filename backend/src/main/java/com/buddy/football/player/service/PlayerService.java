package com.buddy.football.player.service;

import com.buddy.football.player.dto.PlayerListDTO;
import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import com.buddy.football.player.repository.PlayerSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private static final Set<String> VALID_POSITIONS = Set.of(
            "st", "lw", "rw", "cam", "cm", "cdm", "rm", "lm", "cb", "lb", "rb", "gk"
    );

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private static final int MAX_RESULTS = 15;

    @PersistenceContext
    private EntityManager entityManager;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper, EntityManager entityManager) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.entityManager = entityManager;
    }

    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public List<Player> getPlayersByIds(List<UUID> ids) {
        return playerRepository.findAllById(ids);
    }

    public Page<Player> findPlayersByFirstOrLastName(String query, Pageable pageable) {
        return playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);
    }

    public Map<String, List<Player>> getBestPlayersByAttribute() {
        Map<String, List<Player>> bestPlayersByAttribute = new HashMap<>();

        bestPlayersByAttribute.put("dribbling", playerRepository.findTop5ByOrderByMainAttributes_DribblingDesc());
        bestPlayersByAttribute.put("shooting", playerRepository.findTop5ByOrderByMainAttributes_ShootingDesc());
        bestPlayersByAttribute.put("passing", playerRepository.findTop5ByOrderByMainAttributes_PassingDesc());
        bestPlayersByAttribute.put("pace", playerRepository.findTop5ByOrderByMainAttributes_PaceDesc());
        bestPlayersByAttribute.put("physical", playerRepository.findTop5ByOrderByMainAttributes_PhysicalDesc());
        bestPlayersByAttribute.put("defending", playerRepository.findTop5ByOrderByMainAttributes_DefendingDesc());
        bestPlayersByAttribute.put("goalkeeping", playerRepository.findTop5ByOrderByMainAttributes_GoalkeepingDesc());

        return bestPlayersByAttribute;
    }

    public List<Player> getSimilarPlayers(UUID id) {
        Player referencePlayer = getPlayerById(id);

        int overallRating = referencePlayer.getOverallRating();
        int minRating = Math.max(0, overallRating - 2);
        int maxRating = Math.min(100, overallRating + 2);

        UUID leagueId = referencePlayer.getTeam().getLeague().getId();
        List<Player> similarPlayers = playerRepository.findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_Id(
                referencePlayer.getMainPositions(),
                minRating,
                maxRating,
                id,
                leagueId
        );

        if (similarPlayers.size() < 5) {
            List<Player> playersFromOtherLeagues = playerRepository.findPlayersByMainPositionsContainingAndOverallRatingBetweenAndIdIsNotAndTeam_League_IdIsNot(
                    referencePlayer.getMainPositions(),
                    minRating,
                    maxRating,
                    id,
                    leagueId
            );

            for (Player otherPlayer : playersFromOtherLeagues) {
                if (similarPlayers.size() >= 5) {
                    break;
                }
                if (!similarPlayers.contains(otherPlayer)) {
                    similarPlayers.add(otherPlayer);
                }
            }
        }

        return similarPlayers;
    }

    public List<PlayerListDTO> playerScouting(double marketValueMax, String position, int minValue) {
        String validatedPosition = position.toLowerCase();

        if (!VALID_POSITIONS.contains(validatedPosition)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        Specification<Player> spec = PlayerSpecification.filterPlayers(marketValueMax, validatedPosition, minValue);

        Pageable pageable = PageRequest.of(0, MAX_RESULTS);

        List<Player> players = playerRepository.findAll(spec, pageable).getContent();

        return players.stream()
                .map(playerMapper::toListDTO)
                .collect(Collectors.toList());
    }

    public Player getPlayerById(UUID id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }
}
