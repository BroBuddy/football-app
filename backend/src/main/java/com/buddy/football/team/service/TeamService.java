package com.buddy.football.team.service;

import com.buddy.football.player.entity.Player;
import com.buddy.football.simulation.dto.MatchTeamDTO;
import com.buddy.football.simulation.dto.MatchMapper;
import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final MatchMapper matchMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, MatchMapper matchMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.matchMapper = matchMapper;
    }

    public Page<Team> getAllTeams(int page, int size) {
        Pageable sortedByMarketValueDesc = PageRequest.of(page, size, Sort.by("marketValue").descending());
        return teamRepository.findAll(sortedByMarketValueDesc);
    }

    public void updateTeamStats(Team team, List<Player> players) {
        if (players != null && !players.isEmpty()) {
            double totalValue = players.stream()
                    .filter(java.util.Objects::nonNull)
                    .mapToDouble(Player::getMarketValue)
                    .sum();
            team.setMarketValue(totalValue);
            teamRepository.save(team);
        }
    }

    public List<TeamListDTO> getTeamsByLeagueId(UUID leagueId) {
        return teamRepository.findTeamsByLeagueId(leagueId);
    }

    @Transactional
    public Optional<TeamDetailDTO> getTeamDetails(UUID teamId) {
        return teamRepository.findById(teamId)
                .map(teamMapper::toDetailDTO);
    }

    @Transactional
    public Optional<Team> getTeamWithAllDetails(UUID teamId) {
        return teamRepository.findById(teamId);
    }

    @Transactional
    public Optional<MatchTeamDTO> getMatchupTeams(UUID homeId, UUID awayId) {
        Optional<Team> homeTeamOpt = teamRepository.findById(homeId);
        Optional<Team> awayTeamOpt = teamRepository.findById(awayId);

        if (homeTeamOpt.isEmpty() || awayTeamOpt.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new MatchTeamDTO(
                matchMapper.toLineupDTO(homeTeamOpt.get()),
                matchMapper.toLineupDTO(awayTeamOpt.get())
        ));
    }
}