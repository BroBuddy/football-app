package com.buddy.football.bootstrap.data;

import com.buddy.football.league.entity.League;
import com.buddy.football.league.repository.LeagueRepository;
import com.buddy.football.team.entity.Team;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TeamData {

    private static LeagueRepository leagueRepository = null;

    public TeamData(LeagueRepository leagueRepository) {
        TeamData.leagueRepository = leagueRepository;
        Optional<League> leagueOptional = leagueRepository.findByName("Bundesliga");
        League league = leagueOptional.orElseThrow(() -> new RuntimeException("League not found."));
    }

    public static List<Team> get() {
        LocalDateTime now = LocalDateTime.now();

        return List.of(
                        bundesliga(),
                        premierLeague(),
                        laLiga(),
                        serieA(),
                        ligue1()
                ).stream()
                .flatMap(List::stream)
                .map(data -> {
                    String teamName = data[0];
                    String logoCode = data[1];
                    String leagueName = data[2];
                    String nationCode = data[3];

                    League league = leagueRepository.findByName(leagueName)
                            .orElseThrow(() -> new RuntimeException("League nicht gefunden: " + leagueName));

                    Team team = new Team(
                            teamName,
                            "https://football-logos.cc/logos/" + nationCode + "/256x256/" + logoCode + ".png",
                            0.0,
                            league,
                            List.of(),
                            now,
                            now
                    );

                    return team;
                })
                .toList();
    }

    private static List<String[]> bundesliga() {
        return List.of(
                new String[]{"1. FC Heidenheim", "fc-heidenheim", "Bundesliga", "germany"},
                new String[]{"1. FC Köln", "koln", "Bundesliga", "germany"},
                new String[]{"1. FSV Mainz 05", "mainz-05", "Bundesliga", "germany"},
                new String[]{"FC Augsburg", "augsburg", "Bundesliga", "germany"},
                new String[]{"Bayer 04 Leverkusen", "bayer-leverkusen", "Bundesliga", "germany"},
                new String[]{"Borussia Dortmund", "borussia-dortmund", "Bundesliga", "germany"},
                new String[]{"Borussia Mönchengladbach", "borussia-monchengladbach", "Bundesliga", "germany"},
                new String[]{"Eintracht Frankfurt", "eintracht-frankfurt", "Bundesliga", "germany"},
                new String[]{"FC Bayern München", "bayern-munchen", "Bundesliga", "germany"},
                new String[]{"SC Freiburg", "freiburg", "Bundesliga", "germany"},
                new String[]{"Hamburger SV", "hamburger-sv", "Bundesliga", "germany"},
                new String[]{"TSG 1899 Hoffenheim", "hoffenheim", "Bundesliga", "germany"},
                new String[]{"RB Leipzig", "rb-leipzig", "Bundesliga", "germany"},
                new String[]{"SV Werder Bremen", "werder-bremen", "Bundesliga", "germany"},
                new String[]{"VfB Stuttgart", "vfb-stuttgart", "Bundesliga", "germany"},
                new String[]{"VfL Wolfsburg", "wolfsburg", "Bundesliga", "germany"},
                new String[]{"FC St. Pauli", "st-pauli", "Bundesliga", "germany"},
                new String[]{"1. FC Union Berlin", "union-berlin", "Bundesliga", "germany"}
        );
    }

    private static List<String[]> premierLeague() {
        return List.of(
                new String[]{"Manchester City", "manchester-city", "Premier League", "england"},
                new String[]{"Liverpool", "liverpool", "Premier League", "england"},
                new String[]{"Arsenal", "arsenal", "Premier League", "england"},
                new String[]{"Tottenham Hotspur", "tottenham", "Premier League", "england"},
                new String[]{"Newcastle United", "newcastle", "Premier League", "england"},
                new String[]{"Aston Villa", "aston-villa", "Premier League", "england"},
                new String[]{"Manchester United", "manchester-united", "Premier League", "england"},
                new String[]{"Chelsea", "chelsea", "Premier League", "england"},
                new String[]{"Nottingham Forest", "nottingham-forest", "Premier League", "england"},
                new String[]{"Crystal Palace", "crystal-palace", "Premier League", "england"},
                new String[]{"Brighton", "west-bromwich-albion", "Premier League", "england"},
                new String[]{"West Ham United", "west-ham", "Premier League", "england"},
                new String[]{"Everton", "everton", "Premier League", "england"},
                new String[]{"Fulham FC", "fulham", "Premier League", "england"}
        );
    }

    private static List<String[]> laLiga() {
        return List.of(
                new String[]{"Real Madrid", "real-madrid", "La Liga", "spain"},
                new String[]{"Barcelona", "barcelona", "La Liga", "spain"},
                new String[]{"Atlético Madrid", "atletico-madrid", "La Liga", "spain"},
                new String[]{"Athletic Club", "athletic-club", "La Liga", "spain"},
                new String[]{"Real Betis", "real-betis", "La Liga", "spain"},
                new String[]{"Real Sociedad", "real-sociedad", "La Liga", "spain"},
                new String[]{"Villarreal CF", "villarreal", "La Liga", "spain"},
                new String[]{"Girona FC", "girona", "La Liga", "spain"}
        );
    }

    private static List<String[]> serieA() {
        return List.of(
                new String[]{"Inter Milan", "inter", "Serie A", "italy"},
                new String[]{"AC Milan", "milan", "Serie A", "italy"},
                new String[]{"Atalanta", "atalanta", "Serie A", "italy"},
                new String[]{"Juventus", "juventus", "Serie A", "italy"},
                new String[]{"Lazio", "lazio", "Serie A", "italy"},
                new String[]{"Napoli", "napoli", "Serie A", "italy"},
                new String[]{"Roma", "roma", "Serie A", "italy"},
                new String[]{"Fiorentina", "fiorentina", "Serie A", "italy"}
        );
    }

    private static List<String[]> ligue1() {
        return List.of(
                new String[]{"Paris SG", "paris-saint-germain", "Ligue 1", "france"},
                new String[]{"Marseille", "marseille", "Ligue 1", "france"},
                new String[]{"Lille OSC", "lille", "Ligue 1", "france"},
                new String[]{"AS Monaco", "as-monaco", "Ligue 1", "france"}
        );
    }
}