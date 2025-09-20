package com.buddy.football.nation.dto;

import com.buddy.football.nation.entity.Nation;
import org.springframework.stereotype.Component;

@Component
public class NationMapper {
    public NationDTO fromEntity(Nation nation) {
        if (nation == null) {
            throw new IllegalArgumentException("Nation must not be null");
        }

        return new NationDTO(
                nation.getId(),
                nation.getName(),
                nation.getCode(),
                nation.getFlagUrl()
        );
    }
}
