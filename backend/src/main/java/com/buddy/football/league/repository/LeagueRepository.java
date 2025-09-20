package com.buddy.football.league.repository;

import com.buddy.football.league.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeagueRepository extends JpaRepository<League, UUID> {
    Optional<League> findByName(String name);

    @Query("SELECT l FROM League l JOIN FETCH l.nation LEFT JOIN FETCH l.teams")
    List<League> findAllWithNationAndTeams();
}
