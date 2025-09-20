package com.buddy.football.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/crawler")
public class TeamCrawler {

    InputStream getTeamHtmlStream(String team) throws IOException {
        return new ClassPathResource("data/" + team + ".html").getInputStream();
    }

    @GetMapping
    public ResponseEntity<List<String>> getPlayerLinks(@RequestParam String team) {
        if (!team.matches("[a-zA-Z0-9_-]+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(List.of("❌ Ungültiger Teamname"));
        }

        try (InputStream inputStream = getTeamHtmlStream(team)) {  // <-- hier die neue Methode nutzen
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");

            Elements lineupDivs = doc.select("div.lineup");
            Element lineupDiv = lineupDivs.first();

            if (lineupDiv == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(List.of("❌ Kein <div class=\"lineup\"> gefunden"));
            }

            Elements baskets = lineupDiv.select("div.field-basket");

            List<String> playerLinks = baskets.stream()
                    .flatMap(basket -> basket.select("ul li a[href^=https://sofifa.com/player/]").stream())
                    .map(a -> a.attr("href"))
                    .distinct()
                    .collect(Collectors.toList());

            return ResponseEntity.ok(playerLinks);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of("❌ Fehler beim Abrufen der Seite: " + e.getMessage()));
        }
    }
}

