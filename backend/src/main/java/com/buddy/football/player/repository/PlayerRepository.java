package com.buddy.football.player.repository;

import com.buddy.football.player.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID>, JpaSpecificationExecutor<Player> {
    @EntityGraph(attributePaths = {"attributes", "positions", "nation", "team"})
    List<Player> findAll();
    Page<Player> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);
    List<Player> findTop5ByMainPositionsContainingAndOverallRatingBetweenAndIdIsNot(
            String mainPositions,
            Integer minOverallRating,
            Integer maxOverallRating,
            UUID id
    );
    List<Player> findTop5ByOrderByMainAttributes_DribblingDesc();
    List<Player> findTop5ByOrderByMainAttributes_ShootingDesc();
    List<Player> findTop5ByOrderByMainAttributes_PassingDesc();
    List<Player> findTop5ByOrderByMainAttributes_PaceDesc();
    List<Player> findTop5ByOrderByMainAttributes_PhysicalDesc();
    List<Player> findTop5ByOrderByMainAttributes_DefendingDesc();
    List<Player> findTop5ByOrderByMainAttributes_GoalkeepingDesc();
}
