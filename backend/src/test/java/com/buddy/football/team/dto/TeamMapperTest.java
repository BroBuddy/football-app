package com.buddy.football.team.dto;

import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.dto.PlayerTeamDTO;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamMapperTest {

    @Mock
    private NationMapper nationMapper;

    @InjectMocks
    private TeamMapper teamMapper;

    @Test
    void shouldMapToListDTO() {
        Team team = new Team();
        UUID teamId = UUID.randomUUID();
        team.setId(teamId);
        team.setName("Bayer 04 Leverkusen");
        team.setLogoUrl("leverkusen.png");

        team.setMarketValue(100.0);

        team.setPlayers(List.of(
                createPlayer("John", "Doe", 50.0),
                createPlayer("Max", "Mustermann", 50.0)
        ));

        var dto = teamMapper.toListDTO(team);

        assertNotNull(dto);
        assertEquals(teamId, dto.id());
        assertEquals("Bayer 04 Leverkusen", dto.name());
        assertEquals("leverkusen.png", dto.logoUrl());
        assertEquals(100.0, dto.marketValue());
    }

    @Test
    void shouldMapToDetailDTO() {
        Team team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Bayer 04 Leverkusen");
        team.setLogoUrl("leverkusen.png");

        Player startingPlayer = createPlayer("John", "Doe", 10.0);

        team.setPlayers(List.of(startingPlayer));

        TeamDetailDTO dto = teamMapper.toDetailDTO(team);

        assertNotNull(dto);
        assertEquals("Bayer 04 Leverkusen", dto.name());
        assertEquals("leverkusen.png", dto.logoUrl());

        assertEquals(1, dto.startingPlayers().size());
        PlayerTeamDTO startingPlayerDTO = dto.startingPlayers().get(0);
        assertEquals("John", startingPlayerDTO.firstName());
        assertEquals(10.0, startingPlayerDTO.marketValue());
    }

    @Test
    void shouldThrowExceptionForNullTeam() {
        assertThrows(IllegalArgumentException.class, () -> teamMapper.toListDTO(null));
        assertThrows(IllegalArgumentException.class, () -> teamMapper.toDetailDTO(null));
        assertThrows(IllegalArgumentException.class, () -> teamMapper.toBaseDTO(null));
        assertThrows(IllegalArgumentException.class, () -> teamMapper.toLeagueDTO(null));
    }

    private Player createPlayer(String firstName, String lastName, double marketValue) {
        Nation nation = new Nation();
        nation.setName("Germany");
        nation.setCode("DEU");
        LocalDateTime now = LocalDateTime.now();

        return new Player(
                firstName,
                lastName,
                LocalDate.now().minusYears(25),
                180,
                75, marketValue,
                nation,
                null,
                true,
                now,
                now
        );
    }
}
