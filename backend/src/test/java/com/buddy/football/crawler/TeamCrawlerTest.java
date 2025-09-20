package com.buddy.football.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamCrawlerTest {

    private TeamCrawler teamCrawler;

    @BeforeEach
    void setup() {
        teamCrawler = Mockito.spy(new TeamCrawler());
    }

    @Test
    void testGetPlayerLinks_success() throws Exception {
        String team = "testteam";
        String html = """
                <div class="lineup">
                    <div class="field-basket">
                        <ul>
                            <li><a href="https://sofifa.com/player/1">Player1</a></li>
                            <li><a href="https://sofifa.com/player/2">Player2</a></li>
                        </ul>
                    </div>
                </div>
                """;

        InputStream mockedStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedStream).when(teamCrawler).getTeamHtmlStream(team);

        ResponseEntity<List<String>> response = teamCrawler.getPlayerLinks(team);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains("https://sofifa.com/player/1"));
        assertTrue(response.getBody().contains("https://sofifa.com/player/2"));
    }

    @Test
    void testGetPlayerLinks_invalidTeam() {
        ResponseEntity<List<String>> response = teamCrawler.getPlayerLinks("invalid team!");
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(List.of("❌ Ungültiger Teamname"), response.getBody());
    }

    @Test
    void testGetPlayerLinks_noLineup() throws Exception {
        String team = "emptyteam";
        String html = "<div class=\"other-div\"></div>";
        InputStream mockedStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));

        Mockito.doReturn(mockedStream).when(teamCrawler).getTeamHtmlStream(team);

        ResponseEntity<List<String>> response = teamCrawler.getPlayerLinks(team);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(List.of("❌ Kein <div class=\"lineup\"> gefunden"), response.getBody());
    }
}
