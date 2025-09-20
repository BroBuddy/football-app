package com.buddy.football.bootstrap.initializer;

import com.buddy.football.bootstrap.data.TeamData;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class TeamInitializer implements CommandLineRunner {

    private final TeamRepository teamRepository;

    public TeamInitializer(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) {
        if (teamRepository.count() == 0) {
            List<Team> teams = TeamData.get();
            teamRepository.saveAll(teams);
            System.out.println("✅ Teams initialized: " + teams.size());
        } else {
            System.out.println("ℹ️ Teams already exist, skipping initialization");
        }
    }
}
