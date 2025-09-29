package com.buddy.football.bootstrap.data;

import com.buddy.football.league.entity.League;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LeagueData {

    private final NationRepository nationRepository;

    public LeagueData(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    public List<League> get() {
        LocalDateTime now = LocalDateTime.now();

        return getData().stream()
                .map(data -> {
                    String leagueName = (String) data[0];
                    String nationCode = (String) data[1];

                    Nation nation = nationRepository.findByCode(nationCode)
                            .orElseThrow(() -> new RuntimeException("Nation nicht gefunden: " + nationCode));

                    return new League(
                            leagueName,
                            nation,
                            List.of(),
                            now,
                            now
                    );
                })
                .toList();
    }

    private List<Object[]> getData() {
        return List.of(
                new Object[]{"Bundesliga", "DEU"},
                new Object[]{"La Liga", "ESP"},
                new Object[]{"Serie A", "ITA"},
                new Object[]{"Premier League", "ENG"},
                new Object[]{"Ligue 1", "FRA"}
        );
    }
}
