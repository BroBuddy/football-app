package com.buddy.football.team.repository;

import com.buddy.football.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String name);
    List<Team> findAllByLeagueId(UUID leagueId);

    @Query("SELECT t FROM Team t JOIN FETCH t.players p JOIN FETCH p.nation WHERE t.id = :teamId")
    Optional<Team> findTeamWithAllDetails(@Param("teamId") UUID teamId);
}
