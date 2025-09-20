package com.buddy.football.player.entity;

import com.buddy.football.nation.entity.Nation;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private UUID commonId;
    private Nation commonNation;
    private Team commonTeam;
    private LocalDateTime commonCreated;
    private LocalDateTime commonUpdated;
    private LocalDate commonBirthDate;

    @BeforeEach
    void setUp() {
        commonId = UUID.randomUUID();
        commonNation = new Nation("Germany", "DEU");
        commonTeam = new Team();
        commonBirthDate = LocalDate.of(2000, 1, 1);
        commonCreated = LocalDateTime.now();
        commonUpdated = LocalDateTime.now();
    }

    @Test
    void shouldCalculateAgeCorrectly() {
        LocalDate birthDate = LocalDate.now().minusYears(25);
        Player player = new Player("John", "Doe", birthDate, 180, 75, 50.0, null, null, true,
                LocalDateTime.now(), LocalDateTime.now());

        assertEquals(25, player.getAge());
    }

    @Test
    void shouldHandleNullBirthDate() {
        Player player = new Player("Jane", "Doe", null, 170, 60, 40.0, null, null, false,
                LocalDateTime.now(), LocalDateTime.now());

        assertEquals(0, player.getAge());
    }

    @Test
    void shouldBeEqualWhenAllFieldsAreEqual() {
        Player player1 = new Player("John", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);
        player1.setId(commonId);

        Player player2 = new Player("John", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);
        player2.setId(commonId);

        assertEquals(player1, player2);
        assertEquals(player1.hashCode(), player2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenFieldsAreDifferent() {
        Player player1 = new Player("John", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);
        player1.setId(commonId);

        Player playerDifferentId = new Player("John", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);
        playerDifferentId.setId(UUID.randomUUID());

        assertNotEquals(player1, playerDifferentId);

        Player playerDifferentFirstName = new Player("Jane", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);
        playerDifferentFirstName.setId(commonId);

        assertNotEquals(player1, playerDifferentFirstName);
    }

    @Test
    void shouldHandleNullAndDifferentClassInEquals() {
        Player player1 = new Player("John", "Doe", commonBirthDate, 180, 75, 50.0, commonNation, commonTeam, true,
                commonCreated, commonUpdated);

        assertNotEquals(player1, null, "Vergleich mit null sollte false zurückgeben.");

        Object otherObject = new Object();
        assertNotEquals(player1, otherObject, "Vergleich mit einem Objekt einer anderen Klasse sollte false zurückgeben.");
    }

    @Test
    void shouldTestToString() {
        Player player = new Player("John", "Doe", LocalDate.of(2000, 1, 1),
                180, 75, 50.0, new Nation("Germany", "GER"), new Team(),
                true, LocalDateTime.now(), LocalDateTime.now());

        String str = player.toString();
        assertTrue(str.contains("John"));
        assertTrue(str.contains("Doe"));
        assertTrue(str.contains("Germany"));
    }
}
