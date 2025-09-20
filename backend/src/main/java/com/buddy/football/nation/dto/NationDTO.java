package com.buddy.football.nation.dto;

import java.util.UUID;

public record NationDTO(
        UUID id,
        String name,
        String code,
        String flagUrl
) {
}