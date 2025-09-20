package com.buddy.football.league.controller;

import com.buddy.football.league.dto.LeagueDetailDTO;
import com.buddy.football.league.dto.LeagueListDTO;
import com.buddy.football.league.dto.LeagueMapper;
import com.buddy.football.league.service.LeagueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueMapper leagueMapper;

    public LeagueController(LeagueService leagueService, LeagueMapper leagueMapper) {
        this.leagueService = leagueService;
        this.leagueMapper = leagueMapper;
    }

    @GetMapping
    public List<LeagueListDTO> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDetailDTO> getLeagueById(@PathVariable UUID id) {
        return leagueService.getLeagueById(id)
                .map(leagueMapper::toDetailDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
