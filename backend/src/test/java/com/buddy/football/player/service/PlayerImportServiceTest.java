package com.buddy.football.player.service;

import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.dto.PlayerDataDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import com.buddy.football.team.service.TeamService;
import com.buddy.football.util.CsvPlayerReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerImportServiceTest {

    private NationRepository nationRepository;
    private TeamRepository teamRepository;
    private PlayerBuildingService playerBuildingService;
    private PlayerImportService service;
    private TeamService teamService;

    @BeforeEach
    void setup() {
        nationRepository = mock(NationRepository.class);
        teamRepository = mock(TeamRepository.class);
        playerBuildingService = mock(PlayerBuildingService.class);
        teamService = mock(TeamService.class);

        service = new PlayerImportService(nationRepository, teamRepository, playerBuildingService, teamService);
    }

    @Test
    void testImportPlayers() throws Exception {
        Nation germany = new Nation("Germany", "DEU");
        Team leverkusen = new Team("Bayer 04 Leverkusen");

        when(nationRepository.findAll()).thenReturn(List.of(germany));
        when(teamRepository.findAll()).thenReturn(List.of(leverkusen));

        String csvData = "Jonathan,Tah,1996-02-11,195,97,68.5,Germany,Bayer 04 Leverkusen,true,39,32,87,78,53,60,37,27,68,69,53,86,49,85,40,60,85,69,92,26,85,83,36,57,38,79,88,89,84,11,8,7,9,14";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        PlayerAttributes mockAttributes = new PlayerAttributes();
        PlayerPositions mockPositions = new PlayerPositions();
        PlayerMainAttributes mockMainAttributes = new PlayerMainAttributes();

        when(playerBuildingService.buildPlayerAttributes(any(PlayerDataDTO.class), any(Player.class)))
                .thenReturn(mockAttributes);
        when(playerBuildingService.buildPlayerPositions(any(Player.class)))
                .thenReturn(mockPositions);
        when(playerBuildingService.getMainPositionsAsString(mockPositions))
                .thenReturn("ST,CB");
        when(playerBuildingService.calculateOverallRating(mockPositions))
                .thenReturn(85);
        when(playerBuildingService.buildMainAttributes(mockAttributes))
                .thenReturn(mockMainAttributes);

        List<Player> players = service.importPlayers(inputStream);

        assertEquals(1, players.size());
        Player player = players.get(0);
        assertEquals("Jonathan", player.getFirstName());
        assertEquals("Tah", player.getLastName());
        assertEquals(germany, player.getNation());
        assertEquals(leverkusen, player.getTeam());
        assertEquals(mockAttributes, player.getAttributes());
        assertEquals(mockPositions, player.getPositions());
        assertEquals("ST,CB", player.getMainPositions());
        assertEquals(85, player.getOverallRating());
        assertEquals(mockMainAttributes, player.getMainAttributes());

        verify(nationRepository).findAll();
        verify(teamRepository).findAll();
        verify(playerBuildingService).buildPlayerAttributes(any(PlayerDataDTO.class), any(Player.class));
        verify(playerBuildingService).buildPlayerPositions(any(Player.class));
        verify(playerBuildingService).getMainPositionsAsString(mockPositions);
        verify(playerBuildingService).calculateOverallRating(mockPositions);
        verify(playerBuildingService).buildMainAttributes(mockAttributes);
    }

    @Test
    void testImportPlayersThrowsExceptionIfNationOrTeamMissing() {
        try (var mocked = mockStatic(CsvPlayerReader.class)) {
            PlayerDataDTO dto = new PlayerDataDTO(
                    "Jonathan", "Tah", LocalDate.of(1996,2,11),
                    195,97,68.5,"Germany","Bayer 04 Leverkusen",
                    true, null,null,null,null,null,null,null
            );

            mocked.when(() -> CsvPlayerReader.readCsv(any(InputStream.class)))
                    .thenReturn(List.of(dto));

            InputStream inputStream = new ByteArrayInputStream("".getBytes());

            assertThrows(IllegalStateException.class, () -> service.importPlayers(inputStream));
        }
    }

}
