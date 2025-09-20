package com.buddy.football.player.dto;

import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.player.attributes.dto.PlayerAttributesMapper;
import com.buddy.football.player.attributes.dto.PlayerMainAttributesMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.service.PlayerBuildingService;
import com.buddy.football.team.dto.TeamMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PlayerMapper {

    private final PlayerBuildingService playerBuildingService;
    private final PlayerAttributesMapper playerAttributesMapper;
    private final PlayerMainAttributesMapper playerMainAttributesMapper;
    private final TeamMapper teamMapper;
    private final NationMapper nationMapper;

    public PlayerMapper(PlayerBuildingService playerBuildingService, PlayerAttributesMapper playerAttributesMapper, PlayerMainAttributesMapper playerMainAttributesMapper, TeamMapper teamMapper, NationMapper nationMapper) {
        this.playerBuildingService = playerBuildingService;
        this.playerAttributesMapper = playerAttributesMapper;
        this.playerMainAttributesMapper = playerMainAttributesMapper;
        this.teamMapper = teamMapper;
        this.nationMapper = nationMapper;
    }

    public PlayerListDTO toListDTO(Player player) {
        validatePlayer(player);

        return new PlayerListDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getAge(),
                player.getMarketValue(),
                nationMapper.fromEntity(player.getNation()),
                teamMapper.toBaseDTO(player.getTeam()),
                player.getMainPositions(),
                player.getOverallRating()
        );
    }

    public PlayerTeamDTO toTeamDTO(Player player) {
        validatePlayer(player);

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

    public PlayerMatchDTO toMatchDTO(Player player) {
        validatePlayer(player);

        PlayerAttributes attributes = player.getAttributes();
        PlayerCalculations calculations = playerBuildingService.buildPlayerCalculations(attributes);

        return new PlayerMatchDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.isStarting(),
                player.getMarketValue(),
                calculations.metrics()
        );
    }

    public PlayerDetailDTO toDetailDTO(Player player) {
        validatePlayer(player);

        PlayerCalculations calculations = playerBuildingService.buildPlayerCalculations(player.getAttributes());
        Map<String, Integer> sortedPositions = sortPlayerPositions(player);

        return new PlayerDetailDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getAge(),
                player.getHeight(),
                player.getWeight(),
                player.getMarketValue(),
                nationMapper.fromEntity(player.getNation()),
                teamMapper.toBaseDTO(player.getTeam()),
                player.isStarting(),
                playerMainAttributesMapper.fromEntity(player.getMainAttributes()),
                playerAttributesMapper.fromEntity(player.getAttributes()),
                sortedPositions,
                player.getMainPositions(),
                player.getOverallRating(),
                calculations.metrics()
        );
    }

    private void validatePlayer(Player player) {
        if (player == null || player.getAttributes() == null) {
            throw new IllegalArgumentException("Player or Player attributes must not be null");
        }
    }

    private Map<String, Integer> sortPlayerPositions(Player entity) {
        Map<String, Integer> positionsMap = Map.ofEntries(
                Map.entry("lm", entity.getPositions().getLm()),
                Map.entry("rw", entity.getPositions().getRw()),
                Map.entry("rm", entity.getPositions().getRm()),
                Map.entry("lw", entity.getPositions().getLw()),
                Map.entry("rb", entity.getPositions().getRb()),
                Map.entry("lb", entity.getPositions().getLb()),
                Map.entry("cm", entity.getPositions().getCm()),
                Map.entry("cb", entity.getPositions().getCb()),
                Map.entry("cdm", entity.getPositions().getCdm()),
                Map.entry("cam", entity.getPositions().getCam()),
                Map.entry("st", entity.getPositions().getSt()),
                Map.entry("gk", entity.getPositions().getGk())
        );

        return positionsMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
