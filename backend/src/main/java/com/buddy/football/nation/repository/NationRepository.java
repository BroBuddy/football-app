package com.buddy.football.nation.repository;

import com.buddy.football.nation.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NationRepository extends JpaRepository<Nation, UUID> {
    Optional<Nation> findByCode(String code);
    Optional<Nation> findByName(String name);
}