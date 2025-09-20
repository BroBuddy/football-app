package com.buddy.football.player.service;

import com.buddy.football.player.dto.PlayerDetailDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Set<String> VALID_POSITIONS = Set.of(
            "lm", "rw", "rm", "lw", "rb", "lb", "cm", "cb", "cdm", "cam", "st", "gk"
    );

    public PlayerService(PlayerRepository playerRepository, EntityManager entityManager) {
        this.playerRepository = playerRepository;
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
        int minRating = Math.max(0, overallRating - 5);
        int maxRating = Math.min(100, overallRating + 5);

        return playerRepository.findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNot(
                referencePlayer.getMainPositions(),
                minRating,
                maxRating,
                id
        );
    }

    public List<PlayerDetailDTO> filterPlayers(double marketValueMax, String position, int minValue) {
        if (!VALID_POSITIONS.contains(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        String sql = """
            SELECT p.id, p.last_name AS playerName, t.name AS teamName,
                   p.is_starting AS isStarting, p.market_value AS marketValue, 
                   p.main_positions AS mainPositions, p.overall_rating AS overallRating,
                   pp.st, pp.cam, pp.cb, pp.cm, pp.rw, pp.rm, pp.lw, pp.lm, 
                   pp.rb, pp.lb, pp.cdm, pp.gk
            FROM player_positions pp
            JOIN players p ON pp.player_id = p.id
            JOIN teams t ON p.team_id = t.id
            WHERE p.market_value <= :marketValueMax
              AND p.is_starting = false
              AND pp.%s >= :minValue
            ORDER BY p.market_value DESC
            """.formatted(position);

        Query query = entityManager.createNativeQuery(sql, "PlayerDetailMapping");
        query.setParameter("marketValueMax", marketValueMax);
        query.setParameter("minValue", minValue);

        return query.getResultList();
    }

    public Player getPlayerById(UUID id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }
}
