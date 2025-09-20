package com.buddy.football.nation.controller;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import com.buddy.football.nation.service.NationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NationController.class)
class NationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NationService nationService;

    @MockitoBean
    private NationRepository nationRepository;

    @MockitoBean
    private NationMapper nationMapper;

    @Test
    void shouldReturnAllNations() throws Exception {
        Nation nation = new Nation();
        nation.setId(UUID.randomUUID());
        nation.setName("Germany");

        NationDTO dto = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "deu.png");

        when(nationRepository.findAll()).thenReturn(List.of(nation));
        when(nationMapper.fromEntity(nation)).thenReturn(dto);

        mockMvc.perform(get("/api/nations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Germany"));
    }

    @Test
    void shouldReturnNationById() throws Exception {
        UUID id = UUID.randomUUID();
        Nation nation = new Nation();
        nation.setId(id);
        nation.setName("Spain");

        when(nationService.getNationById(id)).thenReturn(Optional.of(nation));

        mockMvc.perform(get("/api/nations/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spain"));
    }

    @Test
    void shouldReturnNotFoundForUnknownId() throws Exception {
        UUID id = UUID.randomUUID();
        when(nationService.getNationById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/nations/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNationByCode() throws Exception {
        Nation nation = new Nation();
        nation.setId(UUID.randomUUID());
        nation.setCode("DEU");
        nation.setName("Germany");

        NationDTO dto = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "deu.png");

        when(nationRepository.findByCode("DEU")).thenReturn(Optional.of(nation));
        when(nationMapper.fromEntity(nation)).thenReturn(dto);

        mockMvc.perform(get("/api/nations/code/deu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("DEU"))
                .andExpect(jsonPath("$.name").value("Germany"));
    }
}
