package com.buddy.football.nation.dto;

import com.buddy.football.nation.entity.Nation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NationMapperTest {

    private NationMapper nationMapper;

    @BeforeEach
    void setUp() {
        nationMapper = new NationMapper();
    }

    @Test
    void fromEntity_shouldMapAllFields() {
        UUID id = UUID.randomUUID();
        Nation nation = new Nation();
        nation.setId(id);
        nation.setName("Germany");
        nation.setCode("DE");
        nation.setFlagUrl("germany.png");

        NationDTO dto = nationMapper.fromEntity(nation);

        assertEquals(id, dto.id());
        assertEquals("Germany", dto.name());
        assertEquals("DE", dto.code());
        assertEquals("germany.png", dto.flagUrl());
    }

    @Test
    void fromEntity_shouldThrowException_whenNationIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> nationMapper.fromEntity(null)
        );

        assertEquals("Nation must not be null", exception.getMessage());
    }
}
