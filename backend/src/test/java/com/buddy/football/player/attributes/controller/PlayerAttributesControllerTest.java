package com.buddy.football.player.attributes.controller;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.attributes.repository.PlayerAttributesRepository;
import com.buddy.football.player.attributes.dto.PlayerAttributesDTO;
import com.buddy.football.player.service.PlayerAttributesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerAttributesControllerTest {

    private PlayerAttributesService service;
    private PlayerAttributesRepository repository;
    private PlayerAttributesController controller;

    @BeforeEach
    void setup() {
        service = mock(PlayerAttributesService.class);
        repository = mock(PlayerAttributesRepository.class);
        controller = new PlayerAttributesController(service, repository);
    }

    @Test
    void testGetAll_returnsDTOs() {
        PlayerAttributes attr1 = mock(PlayerAttributes.class);
        PlayerAttributes attr2 = mock(PlayerAttributes.class);

        when(repository.findAll()).thenReturn(List.of(attr1, attr2));

        List<PlayerAttributesDTO> result = controller.getAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_found() {
        UUID id = UUID.randomUUID();
        PlayerAttributes attr = new PlayerAttributes();
        when(service.getById(id)).thenReturn(Optional.of(attr));

        ResponseEntity<PlayerAttributes> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(attr, response.getBody());
        verify(service, times(1)).getById(id);
    }

    @Test
    void testGetById_notFound() {
        UUID id = UUID.randomUUID();
        when(service.getById(id)).thenReturn(Optional.empty());

        ResponseEntity<PlayerAttributes> response = controller.getById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(service, times(1)).getById(id);
    }

    @Test
    void testGetByPlayerId_found() {
        UUID playerId = UUID.randomUUID();
        PlayerAttributes attr = new PlayerAttributes();
        when(service.getByPlayerId(playerId)).thenReturn(Optional.of(attr));

        ResponseEntity<PlayerAttributes> response = controller.getByPlayerId(playerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(attr, response.getBody());
        verify(service, times(1)).getByPlayerId(playerId);
    }

    @Test
    void testGetByPlayerId_notFound() {
        UUID playerId = UUID.randomUUID();
        when(service.getByPlayerId(playerId)).thenReturn(Optional.empty());

        ResponseEntity<PlayerAttributes> response = controller.getByPlayerId(playerId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(service, times(1)).getByPlayerId(playerId);
    }
}
