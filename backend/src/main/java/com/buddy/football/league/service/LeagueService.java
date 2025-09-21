package com.buddy.football.league.service;

import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.team.entity.Team;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    public LeagueService(LeagueRepository leagueRepository, LeagueMapper leagueMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
    }

    public List<LeagueListDTO> getAllLeagues() {
        List<League> leagues = leagueRepository.findAll();

        return leagues.stream()
                .map(leagueMapper::toListDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<League> getLeagueById(UUID id) {
        return leagueRepository.findById(id)
                .map(league -> {
                    league.getTeams().sort((t1, t2) -> Double.compare(t2.getMarketValue(), t1.getMarketValue()));
                    return league;
                });
    }
}
