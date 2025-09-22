package com.buddy.football.league.service;

import com.buddy.football.league.dto.LeagueDetailDTO;
import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.repository.TeamRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final LeagueMapper leagueMapper;
    private final NationMapper nationMapper;

    public LeagueService(LeagueRepository leagueRepository, TeamRepository teamRepository, LeagueMapper leagueMapper, NationMapper nationMapper) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.leagueMapper = leagueMapper;
        this.nationMapper = nationMapper;
    }

    public List<LeagueListDTO> getAllLeagues() {
        List<League> leagues = leagueRepository.findAll();

        return leagues.stream()
                .map(leagueMapper::toListDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LeagueDetailDTO> getLeagueById(UUID id) {
        Optional<League> leagueOptional = leagueRepository.findById(id);

        if (leagueOptional.isEmpty()) {
            return Optional.empty();
        }

        League league = leagueOptional.get();

        List<TeamListDTO> teams = teamRepository.findTeamsByLeagueId(league.getId());
        NationDTO nationDto = nationMapper.fromEntity(league.getNation());

        return Optional.of(new LeagueDetailDTO(league.getId(), league.getName(), nationDto, teams));
    }
}