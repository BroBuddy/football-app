package com.buddy.football.simulation.dto;

import com.buddy.football.player.dto.PlayerMapper;
import com.buddy.football.player.dto.PlayerMatchDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.team.entity.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchMapper {

    private final PlayerMapper playerMapper;

    public MatchMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public MatchLineupDTO toLineupDTO(Team team) {
        List<PlayerMatchDTO> starters = team.getPlayers().stream()
                .filter(Player::isStarting)
                .map(playerMapper::toMatchDTO)
                .toList();

        return new MatchLineupDTO(
                team.getId(),
                team.getName(),
                team.getLogoUrl(),
                starters,
                team.getMarketValue()
        );
    }
}
