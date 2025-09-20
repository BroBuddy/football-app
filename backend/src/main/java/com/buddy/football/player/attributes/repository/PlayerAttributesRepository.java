package com.buddy.football.player.attributes.repository;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerAttributesRepository extends JpaRepository<PlayerAttributes, UUID> {
    Optional<PlayerAttributes> findByPlayerId(UUID playerId);
}
