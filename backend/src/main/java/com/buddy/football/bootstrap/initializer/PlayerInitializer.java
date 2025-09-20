package com.buddy.football.bootstrap.initializer;

import com.buddy.football.player.entity.Player;
import com.buddy.football.player.repository.PlayerRepository;
import com.buddy.football.player.service.PlayerImportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(4)
public class PlayerInitializer implements CommandLineRunner {

    private final PlayerImportService importService;
    private final PlayerRepository playerRepository;

    public PlayerInitializer(PlayerImportService importService, PlayerRepository playerRepository) {
        this.importService = importService;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) {
        if (playerRepository.count() == 0) {
            String[] leagueFiles = {
                    "data/bundesliga.csv",
                    "data/premier_league.csv",
                    "data/la_liga.csv",
                    "data/serie_a.csv",
                    "data/ligue_1.csv"
            };

            List<Player> allPlayers = new ArrayList<>();

            for (String path : leagueFiles) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

                if (inputStream == null) {
                    throw new IllegalStateException("❌ CSV-Datei nicht gefunden: " + path);
                }

                List<Player> players = importService.importPlayers(inputStream);
                allPlayers.addAll(players);
                System.out.println("✅ " + players.size() + " Spieler aus " + path + " geladen");
            }

            playerRepository.saveAll(allPlayers);
            System.out.println("✅ Gesamtzahl Spieler gespeichert: " + allPlayers.size());
        } else {
            System.out.println("ℹ️ Players already exist, skipping initialization");
        }
    }
}
