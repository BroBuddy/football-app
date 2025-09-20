package com.buddy.football.team.service;

import com.buddy.football.player.entity.Player;
import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Page<Team> getAllTeams(int page, int size) {
        Pageable sortedByMarketValueDesc = PageRequest.of(page, size, Sort.by("marketValue").descending());
        return teamRepository.findAll(sortedByMarketValueDesc);
    }

    public List<Team> getAllTeamsByLeagueId(UUID leagueId) {
        return teamRepository.findAllByLeagueId(leagueId);
    }

    public List<Team> getSortedTeamsByMarketValue() {
        List<Team> allTeams = teamRepository.findAll();

        return allTeams.stream()
                .sorted(Comparator.comparingDouble(Team::getMarketValue).reversed())
                .toList();
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

    @Transactional
    public TeamDetailDTO getTeamDetails(UUID teamId) {
        return teamRepository.findTeamWithAllDetails(teamId)
                .map(teamMapper::toDetailDTO)
                .orElse(null);
    }

    @Transactional
    public Optional<Team> getTeamWithAllDetails(UUID teamId) {
        return teamRepository.findTeamWithAllDetails(teamId);
    }
}
