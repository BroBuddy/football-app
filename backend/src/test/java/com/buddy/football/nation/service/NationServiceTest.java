package com.buddy.football.nation.service;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NationServiceTest {

    private NationRepository nationRepository;
    private NationMapper nationMapper;
    private NationService nationService;

    @BeforeEach
    void setUp() {
        nationRepository = mock(NationRepository.class);
        nationMapper = mock(NationMapper.class);
        nationService = new NationService(nationRepository, nationMapper);
    }

    @Test
    void getAllNations_shouldReturnListOfNations() {
        Nation nation = new Nation();
        nation.setId(UUID.randomUUID());
        nation.setName("Germany");

        when(nationRepository.findAll()).thenReturn(List.of(nation));

        List<Nation> result = nationService.getAllNations();

        assertEquals(1, result.size());
        assertEquals("Germany", result.get(0).getName());
        verify(nationRepository, times(1)).findAll();
    }

    @Test
    void getNationById_shouldReturnNation_whenExists() {
        UUID id = UUID.randomUUID();
        Nation nation = new Nation();
        nation.setId(id);
        nation.setName("Spain");

        when(nationRepository.findById(id)).thenReturn(Optional.of(nation));

        Optional<Nation> result = nationService.getNationById(id);

        assertTrue(result.isPresent());
        assertEquals("Spain", result.get().getName());
        verify(nationRepository, times(1)).findById(id);
    }

    @Test
    void getNationById_shouldReturnEmpty_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(nationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Nation> result = nationService.getNationById(id);

        assertTrue(result.isEmpty());
        verify(nationRepository, times(1)).findById(id);
    }

    @Test
    void getNationByCode_shouldReturnDTO_whenExists() {
        Nation nation = new Nation();
        nation.setId(UUID.randomUUID());
        nation.setCode("DEU");
        nation.setName("Germany");

        NationDTO dto = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "deu.png");

        when(nationRepository.findByCode("DEU")).thenReturn(Optional.of(nation));
        when(nationMapper.fromEntity(nation)).thenReturn(dto);

        Optional<NationDTO> result = nationService.getNationByCode("DEU");

        assertTrue(result.isPresent());
        assertEquals("Germany", result.get().name());
        assertEquals("DEU", result.get().code());
        verify(nationRepository, times(1)).findByCode("DEU");
        verify(nationMapper, times(1)).fromEntity(nation);
    }

    @Test
    void getNationByCode_shouldReturnEmpty_whenNotExists() {
        when(nationRepository.findByCode("XX")).thenReturn(Optional.empty());

        Optional<NationDTO> result = nationService.getNationByCode("XX");

        assertTrue(result.isEmpty());
        verify(nationRepository, times(1)).findByCode("XX");
        verifyNoInteractions(nationMapper);
    }
}
