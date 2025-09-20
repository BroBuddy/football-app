package com.buddy.football.league.dto;

import com.buddy.football.league.entity.League;
import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.player.entity.Player;
import com.buddy.football.team.dto.TeamListDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LeagueMapperTest {

    private TeamMapper teamMapper;
    private NationMapper nationMapper;
    private LeagueMapper leagueMapper;

    @BeforeEach
    void setUp() {
        teamMapper = mock(TeamMapper.class);
        nationMapper = mock(NationMapper.class);
        leagueMapper = new LeagueMapper(teamMapper, nationMapper);
    }

    @Test
    void shouldMapToListDTO() {
        UUID leagueId = UUID.randomUUID();
        League league = new League();
        league.setId(leagueId);
        league.setName("Premier League");

        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "England", "ENG", "eng.png");
        when(nationMapper.fromEntity(any())).thenReturn(nationDTO);

        Team team = new Team();
        team.setName("Arsenal");

        Player player1 = new Player();
        player1.setMarketValue(30.0);
        Player player2 = new Player();
        player2.setMarketValue(20.0);
        team.setPlayers(List.of(player1, player2));

        league.setTeams(List.of(team));

        when(teamMapper.toListDTO(any(Team.class)))
                .thenAnswer(invocation -> {
                    Team t = invocation.getArgument(0);
                    double totalMarketValue = t.getPlayers().stream()
                            .mapToDouble(Player::getMarketValue)
                            .sum();
                    return new TeamListDTO(t.getName(), totalMarketValue);
                });

        var dto = leagueMapper.toDetailDTO(league);

        assertNotNull(dto);
        assertEquals(1, dto.teams().size());
        assertEquals(50.0, dto.teams().get(0).marketValue()); // 30 + 20
    }

    @Test
    void shouldMapToDetailDTO() {
        UUID leagueId = UUID.randomUUID();
        League league = new League();
        league.setId(leagueId);
        league.setName("Premier League");

        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "England", "ENG", "eng.png");
        when(nationMapper.fromEntity(any())).thenReturn(nationDTO);

        Team team = new Team();
        team.setName("Arsenal");

        Player player1 = new Player();
        player1.setMarketValue(30.0);
        Player player2 = new Player();
        player2.setMarketValue(20.0);
        team.setPlayers(List.of(player1, player2));

        league.setTeams(List.of(team));

        when(teamMapper.toListDTO(any(Team.class)))
                .thenAnswer(invocation -> {
                    Team t = invocation.getArgument(0);
                    double totalMarketValue = t.getPlayers().stream()
                            .mapToDouble(Player::getMarketValue)
                            .sum();
                    return new TeamListDTO(t.getName(), totalMarketValue);
                });

        var dto = leagueMapper.toDetailDTO(league);

        assertNotNull(dto);
        assertEquals(leagueId, dto.id());
        assertEquals("Premier League", dto.name());
        assertEquals(nationDTO, dto.nation());
        assertEquals(1, dto.teams().size());
        assertEquals(50.0, dto.teams().get(0).marketValue());
    }

    @Test
    void shouldThrowExceptionIfLeagueIsNull() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> leagueMapper.toListDTO(null));
        assertEquals("League must not be null", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> leagueMapper.toDetailDTO(null));
        assertEquals("League must not be null", exception2.getMessage());
    }
}
