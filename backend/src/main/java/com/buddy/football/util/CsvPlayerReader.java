package com.buddy.football.util;

import com.buddy.football.player.attributes.entity.*;
import com.buddy.football.player.dto.PlayerDataDTO;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvPlayerReader {

    public static List<PlayerDataDTO> readCsv(InputStream inputStream) {
        List<PlayerDataDTO> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length != 43) {
                    throw new IllegalArgumentException("Ungültige Anzahl an Feldern: " + Arrays.toString(fields));
                }

                String firstName = fields[0];
                String lastName = fields[1];
                LocalDate birthDate = LocalDate.parse(fields[2]);
                int height = Integer.parseInt(fields[3]);
                int weight = Integer.parseInt(fields[4]);
                Double marketValue = Double.valueOf(fields[5]);
                String nation = fields[6];
                String team = fields[7];
                boolean isStarting = Boolean.parseBoolean(fields[8]);

                int[] attrs = new int[34];
                for (int i = 0; i < 34; i++) {
                    attrs[i] = Integer.parseInt(fields[9 + i].trim());
                }

                PlayerDataDTO player = buildPlayerObject(firstName, lastName, birthDate, height, weight, marketValue, nation, team, isStarting, attrs);

                players.add(player);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen der CSV-Datei", e);
        }

        return players;
    }

    private static PlayerDataDTO buildPlayerObject(String firstName, String lastName, LocalDate birthDate, int height, int weight, Double marketValue, String nation, String team, boolean isStarting, int[] attrs) {
        return new PlayerDataDTO(
                firstName, lastName, birthDate, height, weight, marketValue, nation, team, isStarting,
                new AttackingAttributes(attrs[0], attrs[1], attrs[2], attrs[3], attrs[4]),
                new SkillAttributes(attrs[5], attrs[6], attrs[7], attrs[8], attrs[9]),
                new MovementAttributes(attrs[10], attrs[11], attrs[12], attrs[13], attrs[14]),
                new PowerAttributes(attrs[15], attrs[16], attrs[17], attrs[18], attrs[19]),
                new MentalityAttributes(attrs[20], attrs[21], attrs[22], attrs[23], attrs[24], attrs[25]),
                new DefendingAttributes(attrs[26], attrs[27], attrs[28]),
                new GoalkeepingAttributes(attrs[29], attrs[30], attrs[31], attrs[32], attrs[33])
        );
    }
}
