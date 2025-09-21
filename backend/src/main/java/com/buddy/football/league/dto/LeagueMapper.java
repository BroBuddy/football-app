package com.buddy.football.league.dto;

import com.buddy.football.league.entity.League;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class LeagueMapper {

    private final TeamMapper teamMapper;
    private final NationMapper nationMapper;

    public LeagueMapper(TeamMapper teamMapper, NationMapper nationMapper) {
        this.teamMapper = teamMapper;
        this.nationMapper = nationMapper;
    }

    public LeagueListDTO toListDTO(League league) {
        validateLeague(league);

        double marketValue = league.getTeams().stream()
                .mapToDouble(Team::getMarketValue)
                .sum();

        return new LeagueListDTO(
                league.getId(),
                league.getName(),
                nationMapper.fromEntity(league.getNation()),
                marketValue
        );
    }

    public LeagueDetailDTO toDetailDTO(League league) {
        validateLeague(league);

        return new LeagueDetailDTO(
                league.getId(),
                league.getName(),
                nationMapper.fromEntity(league.getNation()),
                league.getTeams().stream()
                        .sorted(Comparator.comparingDouble(Team::getMarketValue))
                        .map(teamMapper::toListDTO)
                        .toList()
        );
    }

    private void validateLeague(League league) {
        if (league == null) {
            throw new IllegalArgumentException("League must not be null");
        }
    }
}
