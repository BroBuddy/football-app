package com.buddy.football.team.controller;

import com.buddy.football.team.dto.TeamDetailDTO;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @GetMapping
    public Page<TeamListDTO> getAllTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Team> teamsPage = teamService.getAllTeams(page, size);

        List<TeamListDTO> teamListDTOs = teamsPage.getContent().stream()
                .map(teamMapper::toListDTO)
                .toList();

        return new PageImpl<>(teamListDTOs, teamsPage.getPageable(), teamsPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDetailDTO> getTeamById(@PathVariable UUID id) {
        TeamDetailDTO teamDetails = teamService.getTeamDetails(id);

        if (teamDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(teamDetails);
    }
}
