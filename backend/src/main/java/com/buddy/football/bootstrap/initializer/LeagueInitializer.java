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

    public LeagueInitializer(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public void run(String... args) {
        if (leagueRepository.count() == 0) {
            List<League> leagues = LeagueData.get();
            leagueRepository.saveAll(leagues);
            System.out.println("✅ Leagues initialized: " + leagues.size());
        } else {
            System.out.println("ℹ️ Leagues already exist, skipping initialization");
        }
    }
}
