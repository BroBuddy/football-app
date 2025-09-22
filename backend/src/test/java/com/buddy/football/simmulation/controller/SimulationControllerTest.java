package com.buddy.football.simmulation.controller;

import com.buddy.football.simulation.controller.SimulationController;
import com.buddy.football.simulation.dto.MatchResultDTO;
import com.buddy.football.simulation.service.MatchSimulationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationControllerTest {

    @Mock
    private MatchSimulationService matchSimulationService;

    @InjectMocks
    private SimulationController simulationController;

    @Test
    void shouldSimulateMatchAndReturnResult() {
        // Given
        UUID homeId = UUID.randomUUID();
        UUID awayId = UUID.randomUUID();

        MatchResultDTO expectedResult = new MatchResultDTO("Home Team", 2, "Away Team", 1);

        when(matchSimulationService.simulateMatch(homeId, awayId)).thenReturn(expectedResult);

        // When
        ResponseEntity<MatchResultDTO> response = simulationController.simulateMatch(homeId, awayId);

        // Then
        assertNotNull(response.getBody());
        assertEquals(expectedResult, response.getBody());
    }
}