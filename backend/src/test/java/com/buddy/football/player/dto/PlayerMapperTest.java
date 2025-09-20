package com.buddy.football.player.dto;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.player.attributes.dto.PlayerAttributesDTO;
import com.buddy.football.player.attributes.dto.PlayerAttributesMapper;
import com.buddy.football.player.attributes.dto.PlayerMainAttributesDTO;
import com.buddy.football.player.attributes.dto.PlayerMainAttributesMapper;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.player.repository.PlayerRepository;
import com.buddy.football.player.service.PlayerBuildingService;
import com.buddy.football.team.dto.TeamBaseDTO;
import com.buddy.football.team.dto.TeamMapper;
import com.buddy.football.team.entity.Team;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerMapperTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TeamMapper teamMapper;
    @Mock
    private NationMapper nationMapper;
    @Mock
    private EntityManager entityManager;
    @Mock
    private PlayerBuildingService playerBuildingService;

    private PlayerMapper playerMapper;

    @BeforeEach
    void setUp() {
        playerMapper = new PlayerMapper(
                playerBuildingService,
                new PlayerAttributesMapper(),
                new PlayerMainAttributesMapper(),
                teamMapper,
                nationMapper
        );
    }

    @Test
    void shouldMapToListDTO() {
        Player player = new Player("John", "Doe", null, 180, 75, 50.0, null, null, true, null, null);

        player.setAttributes(new PlayerAttributes());
        player.setMainAttributes(new PlayerMainAttributes());

        Team team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Bayer 04 Leverkusen");
        player.setTeam(team);

        Nation nation = new Nation("Germany", "DEU");
        player.setNation(nation);

        when(teamMapper.toBaseDTO(player.getTeam()))
                .thenReturn(new TeamBaseDTO(player.getTeam().getId(), player.getTeam().getName()));
        when(nationMapper.fromEntity(player.getNation()))
                .thenReturn(new NationDTO(player.getNation().getId(), player.getNation().getName(), player.getNation().getCode(), player.getNation().getFlagUrl()));

        PlayerListDTO dto = playerMapper.toListDTO(player);

        assertEquals(player.getId(), dto.id());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());
        assertEquals(player.getTeam().getId(), dto.team().id());
        assertEquals(player.getTeam().getName(), dto.team().name());
        assertEquals(player.getNation().getName(), dto.nation().name());
    }

    @Test
    void shouldMapToDetailDTO() {
        Player player = new Player("John", "Doe", null, 180, 75, 50.0, null, null, true, null, null);
        player.setMainPositions("ST");

        player.setPositions(new PlayerPositions(95, 90, 80, 75, 70, 65, 60, 55, 50, 45, 40, 30));
        PlayerAttributes attributes = new PlayerAttributes();
        player.setAttributes(attributes);

        Team team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Bayer 04 Leverkusen");
        player.setTeam(team);

        Nation nation = new Nation("Germany", "DEU");
        player.setNation(nation);

        PlayerMainAttributes mainAttributes = new PlayerMainAttributes();
        player.setMainAttributes(mainAttributes);

        when(playerBuildingService.buildPlayerCalculations(player.getAttributes()))
                .thenReturn(new PlayerCalculations(new LinkedHashMap<>()));

        when(teamMapper.toBaseDTO(player.getTeam()))
                .thenReturn(new TeamBaseDTO(player.getTeam().getId(), player.getTeam().getName()));
        when(nationMapper.fromEntity(player.getNation()))
                .thenReturn(new NationDTO(player.getNation().getId(), player.getNation().getName(), player.getNation().getCode(), player.getNation().getFlagUrl()));

        PlayerDetailDTO dto = playerMapper.toDetailDTO(player);
        assertEquals(player.getId(), dto.id());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());

        assertEquals(PlayerAttributesDTO.class, dto.attributes().getClass());

        assertEquals(PlayerMainAttributesDTO.class, dto.mainAttributes().getClass());

        assertEquals(player.getTeam().getId(), dto.team().id());
        assertEquals(player.getNation().getName(), dto.nation().name());
        assertEquals("ST", dto.mainPositions());
    }

    @Test
    void shouldMapToMatchDTO() {
        UUID playerId = UUID.randomUUID();
        Player player = new Player();
        player.setId(playerId);
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setStarting(true);
        player.setMarketValue(50.0);
        player.setAttributes(new PlayerAttributes());

        Map<String, Double> metrics = new LinkedHashMap<>();
        metrics.put("xG", 0.75);
        metrics.put("xA", 0.50);

        when(playerBuildingService.buildPlayerCalculations(any(PlayerAttributes.class)))
                .thenReturn(new PlayerCalculations(metrics));

        PlayerMatchDTO dto = playerMapper.toMatchDTO(player);

        assertNotNull(dto);
        assertEquals(playerId, dto.id());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());
        assertEquals(true, dto.isStarting());
        assertEquals(50.0, dto.marketValue());
        assertEquals(metrics, dto.metrics());
    }

    @Test
    void shouldMapToTeamDTO() {
        UUID playerId = UUID.randomUUID();
        Nation nation = new Nation("Germany", "DEU");
        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Germany", "DEU", "deu.png");

        Player player = new Player();
        player.setId(playerId);
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setBirthDate(LocalDate.of(2000, 1, 1));
        player.setMarketValue(75.0);
        player.setNation(nation);
        player.setMainPositions("CM");
        player.setOverallRating(88);

        player.setAttributes(new PlayerAttributes());

        when(nationMapper.fromEntity(any(Nation.class)))
                .thenReturn(nationDTO);

        PlayerTeamDTO dto = playerMapper.toTeamDTO(player);

        assertNotNull(dto);
        assertEquals(playerId, dto.id());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());
        assertEquals(25, dto.age());
        assertEquals(75.0, dto.marketValue());
        assertEquals(nationDTO, dto.nation());
        assertEquals("CM", dto.mainPositions());
        assertEquals(88, dto.overallRating());
    }

    @Test
    void shouldSortPlayerPositions() {
        Player player = new Player();
        player.setAttributes(new PlayerAttributes());
        player.setPositions(new PlayerPositions(
                95, 90, 80, 75, 70, 65, 60, 55, 50, 45, 40, 30
        ));
        player.setMainPositions("LM");
        player.setOverallRating(95);
        player.setHeight(180);
        player.setWeight(75);
        player.setMarketValue(50.0);
        player.setNation(new Nation("Germany", "DEU"));
        player.setTeam(new Team());

        when(playerBuildingService.buildPlayerCalculations(any(PlayerAttributes.class)))
                .thenReturn(new PlayerCalculations(new LinkedHashMap<>()));
        when(nationMapper.fromEntity(any(Nation.class)))
                .thenReturn(new NationDTO(UUID.randomUUID(), "Germany", "DEU", "deu.png"));
        when(teamMapper.toBaseDTO(any(Team.class)))
                .thenReturn(new TeamBaseDTO(UUID.randomUUID(), "Germany"));

        PlayerDetailDTO dto = playerMapper.toDetailDTO(player);

        assertNotNull(dto.positions());
        assertEquals(LinkedHashMap.class, dto.positions().getClass());

        List<String> actualKeys = new ArrayList<>(dto.positions().keySet());
        List<String> expectedKeys = List.of("lm", "rw", "rm", "lw", "rb", "lb", "cm", "cb", "cdm", "cam", "st", "gk");

        List<Integer> actualValues = new ArrayList<>(dto.positions().values());
        List<Integer> expectedValues = List.of(95, 90, 80, 75, 70, 65, 60, 55, 50, 45, 40, 30);

        assertEquals(expectedKeys, actualKeys, "The position keys should be sorted correctly.");
        assertEquals(expectedValues, actualValues, "The position values should be sorted correctly.");
    }
}