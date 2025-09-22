package com.buddy.football.team.dto;

import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.player.dto.PlayerTeamDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.team.entity.Team;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamMapper {
    private final NationMapper nationMapper;

    public TeamMapper(NationMapper nationMapper) {
        this.nationMapper = nationMapper;
    }

    public TeamListDTO toListDTO(Team team) {
        validateTeam(team);

        return new TeamListDTO(
                team.getId(),
                team.getName(),
                team.getLogoUrl(),
                team.getMarketValue(),
                team.getPlayers().size()
        );
    }

    public TeamBaseDTO toBaseDTO(Team team) {
        validateTeam(team);

        return new TeamBaseDTO(
                team.getId(),
                team.getName(),
                team.getLogoUrl()
        );
    }

    public TeamLeagueDTO toLeagueDTO(Team team) {
        validateTeam(team);

        return new TeamLeagueDTO(
                team.getId(),
                team.getName(),
                team.getLogoUrl(),
                team.getMarketValue()
        );
    }

    public TeamDetailDTO toDetailDTO(Team team) {
        validateTeam(team);

        List<Player> sortedPlayers = team.getPlayers().stream()
                .sorted(Comparator.comparing(Player::getOverallRating).reversed())
                .collect(Collectors.toList());

        List<PlayerTeamDTO> startingPlayers = sortedPlayers.stream()
                .filter(Player::isStarting)
                .map(this::toPlayerTeamDTO)
                .collect(Collectors.toList());

        List<PlayerTeamDTO> restingPlayers = sortedPlayers.stream()
                .filter(player -> !player.isStarting())
                .map(this::toPlayerTeamDTO)
                .collect(Collectors.toList());

        return new TeamDetailDTO(
                team.getId(),
                team.getName(),
                team.getLogoUrl(),
                team.getMarketValue(),
                startingPlayers,
                restingPlayers
        );
    }

    private PlayerTeamDTO toPlayerTeamDTO(Player player) {
        return new PlayerTeamDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getAge(),
                player.getMarketValue(),
                nationMapper.fromEntity(player.getNation()),
                player.getMainPositions(),
                player.getOverallRating()
        );
    }

    private void validateTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team must not be null");
        }
    }
}