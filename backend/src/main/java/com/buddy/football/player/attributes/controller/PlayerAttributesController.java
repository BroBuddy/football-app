package com.buddy.football.player.attributes.controller;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.attributes.repository.PlayerAttributesRepository;
import com.buddy.football.player.attributes.dto.PlayerAttributesDTO;
import com.buddy.football.player.attributes.dto.PlayerAttributesMapper;
import com.buddy.football.player.service.PlayerAttributesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/player-attributes")
public class PlayerAttributesController {

    private final PlayerAttributesService service;
    private final PlayerAttributesRepository repository;

    public PlayerAttributesController(PlayerAttributesService service, PlayerAttributesRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<PlayerAttributesDTO> getAll() {
        return repository.findAll().stream()
                .map(PlayerAttributesMapper::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerAttributes> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<PlayerAttributes> getByPlayerId(@PathVariable UUID playerId) {
        return service.getByPlayerId(playerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
