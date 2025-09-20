package com.buddy.football.player.service;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.attributes.repository.PlayerAttributesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerAttributesService {

    private final PlayerAttributesRepository repository;

    public PlayerAttributesService(PlayerAttributesRepository repository) {
        this.repository = repository;
    }

    public Optional<PlayerAttributes> getById(UUID id) {
        return repository.findById(id);
    }

    public Optional<PlayerAttributes> getByPlayerId(UUID playerId) {
        return repository.findByPlayerId(playerId);
    }
}
