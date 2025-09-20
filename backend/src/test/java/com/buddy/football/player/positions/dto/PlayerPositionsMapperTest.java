package com.buddy.football.player.positions.dto;

import com.buddy.football.player.positions.entity.PlayerPositions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerPositionsMapperTest {

    @Test
    void shouldMapEntityToDtoCorrectly() {
        PlayerPositions entity = new PlayerPositions(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        PlayerPositionsDTO dto = PlayerPositionsMapper.fromEntity(entity);

        assertThat(dto.lm()).isEqualTo(1);
        assertThat(dto.rw()).isEqualTo(2);
        assertThat(dto.rm()).isEqualTo(3);
        assertThat(dto.lw()).isEqualTo(4);
        assertThat(dto.rb()).isEqualTo(5);
        assertThat(dto.lb()).isEqualTo(6);
        assertThat(dto.cm()).isEqualTo(7);
        assertThat(dto.cb()).isEqualTo(8);
        assertThat(dto.cdm()).isEqualTo(9);
        assertThat(dto.cam()).isEqualTo(10);
        assertThat(dto.st()).isEqualTo(11);
        assertThat(dto.gk()).isEqualTo(12);
    }

    @Test
    void shouldThrowExceptionWhenEntityIsNull() {
        PlayerPositions entity = null;

        assertThrows(NullPointerException.class, () -> PlayerPositionsMapper.fromEntity(entity));
    }
}