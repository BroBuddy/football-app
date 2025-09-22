package com.buddy.football.team.repository;

import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.entity.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    @EntityGraph(attributePaths = "players")
    Optional<Team> findById(UUID teamId);

    @Query("SELECT new com.buddy.football.team.dto.TeamListDTO(t.id, t.name, t.logoUrl, t.marketValue, SIZE(t.players)) " +
            "FROM Team t WHERE t.league.id = :leagueId " +
            "ORDER BY t.marketValue DESC")
    List<TeamListDTO> findTeamsByLeagueId(@Param("leagueId") UUID leagueId);
}