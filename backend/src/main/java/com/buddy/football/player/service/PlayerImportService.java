package com.buddy.football.player.service;

import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.dto.PlayerDataDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import com.buddy.football.team.service.TeamService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.buddy.football.util.CsvPlayerReader.readCsv;

@Service
public class PlayerImportService {

    private final NationRepository nationRepository;
    private final TeamRepository teamRepository;
    private final PlayerBuildingService playerBuildingService;
    private final TeamService teamService;

    public PlayerImportService(NationRepository nationRepository, TeamRepository teamRepository, PlayerBuildingService playerBuildingService, TeamService teamService) {
        this.nationRepository = nationRepository;
        this.teamRepository = teamRepository;
        this.playerBuildingService = playerBuildingService;
        this.teamService = teamService;
    }

    public List<Player> importPlayers(InputStream inputStream) {
        List<PlayerDataDTO> rawPlayers = readCsv(inputStream);

        List<Player> importedPlayers = rawPlayers.stream()
                .map(dto -> {
                    Player player = buildPlayerBase(dto);
                    setPlayerProperties(dto, player);
                    return player;
                })
                .toList();

        Map<Team, List<Player>> playersByTeam = importedPlayers.stream()
                .collect(Collectors.groupingBy(Player::getTeam));

        playersByTeam.forEach((team, players) -> {
            teamService.updateTeamStats(team, players);
        });

        return importedPlayers;
    }


    private Player buildPlayerBase(PlayerDataDTO dto) {
        Map<String, Nation> nationMap = nationRepository.findAll().stream()
                .collect(Collectors.toMap(n -> n.getName().toLowerCase(), n -> n));

        Map<String, Team> teamMap = teamRepository.findAll().stream()
                .collect(Collectors.toMap(t -> t.getName().toLowerCase(), t -> t));

        Nation nation = nationMap.get(dto.nationName().toLowerCase());
        Team team = teamMap.get(dto.teamName().toLowerCase());

        if (nation == null || team == null) {
            throw new IllegalStateException("Nation oder Team nicht gefunden: " + dto.nationName() + ", " + dto.teamName());
        }

        LocalDateTime now = LocalDateTime.now();

        return new Player(
                dto.firstName(),
                dto.lastName(),
                dto.birthDate(),
                dto.height(),
                dto.weight(),
                dto.marketValue(),
                nation,
                team,
                dto.isStarting(),
                now,
                now
        );
    }

    private void setPlayerProperties(PlayerDataDTO dto, Player player) {
        PlayerAttributes attributes = playerBuildingService.buildPlayerAttributes(dto, player);
        player.setAttributes(attributes);

        PlayerPositions positions = playerBuildingService.buildPlayerPositions(player);
        player.setPositions(positions);

        String mainPos = playerBuildingService.getMainPositionsAsString(positions);
        player.setMainPositions(mainPos);

        Integer rating = playerBuildingService.calculateOverallRating(positions);
        player.setOverallRating(rating);

        PlayerMainAttributes mainAttributes = playerBuildingService.buildMainAttributes(attributes);
        player.setMainAttributes(mainAttributes);
    }
}
