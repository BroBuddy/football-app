package com.buddy.football.bootstrap.initializer;

import com.buddy.football.bootstrap.data.LeagueData;
import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class LeagueInitializer implements CommandLineRunner {

    private final LeagueRepository leagueRepository;
    private final LeagueData leagueData;

    public LeagueInitializer(LeagueRepository leagueRepository, LeagueData leagueData) {
        this.leagueRepository = leagueRepository;
        this.leagueData = leagueData;
    }

    @Override
    public void run(String... args) {
        if (leagueRepository.count() == 0) {
            List<League> leagues = leagueData.get();
            leagueRepository.saveAll(leagues);
            System.out.println("✅ Leagues initialized: " + leagues.size());
        } else {
            System.out.println("ℹ️ Leagues already exist, skipping initialization");
        }
    }
}
