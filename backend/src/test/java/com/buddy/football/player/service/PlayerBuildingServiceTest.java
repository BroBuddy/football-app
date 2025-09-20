package com.buddy.football.player.service;

import com.buddy.football.player.attributes.entity.*;
import com.buddy.football.player.calculator.PlayerAttributeCalculator;
import com.buddy.football.player.calculator.PlayerPositionCalculator;
import com.buddy.football.player.dto.PlayerCalculations;
import com.buddy.football.player.dto.PlayerDataDTO;
import com.buddy.football.player.entity.Player;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.util.metric.MetricCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerBuildingServiceTest {

    @Mock
    private PlayerPositionCalculator positionCalculator;
    @Mock
    private PlayerAttributeCalculator attributeCalculator;
    @Mock
    private MetricCalculator metricCalculator;

    @InjectMocks
    private PlayerBuildingService service;

    @BeforeEach
    void setup() {
        positionCalculator = mock(PlayerPositionCalculator.class);
        attributeCalculator = mock(PlayerAttributeCalculator.class);
        metricCalculator = mock(MetricCalculator.class);

        service = new PlayerBuildingService(positionCalculator, attributeCalculator, metricCalculator);
    }

    @Test
    void testBuildPlayerAttributes() {
        Player player = new Player("Jonathan", "Tah");

        PlayerDataDTO dto = new PlayerDataDTO(
                "Jonathan",
                "Tah",
                LocalDate.of(1996, 2, 11),
                195,
                97,
                68.5,
                "Germany",
                "Bayer 04 Leverkusen",
                true,
                new AttackingAttributes(39, 32, 87, 78, 53),
                new SkillAttributes(60, 37, 27, 68, 69),
                new MovementAttributes(53, 86, 49, 85, 11),
                new PowerAttributes(40, 60, 85, 69, 22),
                new MentalityAttributes(92, 26, 85, 83, 33, 44),
                new DefendingAttributes(36, 57, 38),
                new GoalkeepingAttributes(88, 89, 84, 11, 44)
        );

        PlayerAttributes attributes = service.buildPlayerAttributes(dto, player);

        // Attacking
        assertEquals(39, attributes.getAttackingAttributes().getCrossing());
        assertEquals(32, attributes.getAttackingAttributes().getFinishing());
        assertEquals(87, attributes.getAttackingAttributes().getHeadingAccuracy());
        assertEquals(78, attributes.getAttackingAttributes().getShortPassing());
        assertEquals(53, attributes.getAttackingAttributes().getVolleys());

        // Skill
        assertEquals(60, attributes.getSkillAttributes().getDribbling());
        assertEquals(37, attributes.getSkillAttributes().getCurve());
        assertEquals(27, attributes.getSkillAttributes().getFreeKickAccuracy());
        assertEquals(68, attributes.getSkillAttributes().getLongPassing());
        assertEquals(69, attributes.getSkillAttributes().getBallControl());

        // Movement
        assertEquals(53, attributes.getMovementAttributes().getAcceleration());
        assertEquals(86, attributes.getMovementAttributes().getSprintSpeed());
        assertEquals(49, attributes.getMovementAttributes().getAgility());
        assertEquals(85, attributes.getMovementAttributes().getReactions());
        assertEquals(11, attributes.getMovementAttributes().getBalance());

        // Power
        assertEquals(40, attributes.getPowerAttributes().getShotPower());
        assertEquals(60, attributes.getPowerAttributes().getJumping());
        assertEquals(85, attributes.getPowerAttributes().getStamina());
        assertEquals(69, attributes.getPowerAttributes().getStrength());
        assertEquals(22, attributes.getPowerAttributes().getLongShots());

        // Mentality
        assertEquals(92, attributes.getMentalityAttributes().getAggression());
        assertEquals(26, attributes.getMentalityAttributes().getInterceptions());
        assertEquals(85, attributes.getMentalityAttributes().getAttPosition());
        assertEquals(83, attributes.getMentalityAttributes().getVision());
        assertEquals(33, attributes.getMentalityAttributes().getPenalties());
        assertEquals(44, attributes.getMentalityAttributes().getComposure());

        // Defending
        assertEquals(36, attributes.getDefendingAttributes().getDefensiveAwareness());
        assertEquals(57, attributes.getDefendingAttributes().getStandingTackle());
        assertEquals(38, attributes.getDefendingAttributes().getSlidingTackle());

        // Goalkeeping
        assertEquals(88, attributes.getGoalkeepingAttributes().getGkDiving());
        assertEquals(89, attributes.getGoalkeepingAttributes().getGkHandling());
        assertEquals(84, attributes.getGoalkeepingAttributes().getGkKicking());
        assertEquals(11, attributes.getGoalkeepingAttributes().getGkPositioning());
        assertEquals(44, attributes.getGoalkeepingAttributes().getGkReflexes());
    }

    @Test
    void testBuildPlayerPositions() {
        Player player = new Player("John", "Doe", null, 180, 75, 50.0, null, null, true, null, null);
        player.setId(UUID.randomUUID());
        PlayerAttributes attributes = new PlayerAttributes();
        player.setAttributes(attributes);

        Map<String, Integer> expectedPositionRatings = new HashMap<>();
        expectedPositionRatings.put("st", 80);
        expectedPositionRatings.put("cam", 70);
        expectedPositionRatings.put("cb", 60);
        expectedPositionRatings.put("cm", 75);
        expectedPositionRatings.put("rw", 85);
        expectedPositionRatings.put("rm", 85);
        expectedPositionRatings.put("lw", 80);
        expectedPositionRatings.put("lm", 80);
        expectedPositionRatings.put("rb", 70);
        expectedPositionRatings.put("lb", 70);
        expectedPositionRatings.put("cdm", 65);
        expectedPositionRatings.put("gk", 90);

        when(positionCalculator.calculatePositionRatings(any()))
                .thenReturn(expectedPositionRatings);
        when(attributeCalculator.extractAttributes(attributes))
                .thenReturn(Map.of("stamina", 50, "speed", 60));

        PlayerPositions actualPositions = service.buildPlayerPositions(player);

        assertEquals(player, actualPositions.getPlayer());
        assertNotNull(actualPositions.getCreated());
        assertNotNull(actualPositions.getUpdated());

        Map<String, Integer> actualPositionMap = Stream.of(
                "st", "cam", "cb", "cm", "rw", "rm", "lw", "lm", "rb", "lb", "cdm", "gk"
        ).collect(Collectors.toMap(
                String::toLowerCase,
                p -> actualPositions.getPositionValue(p)
        ));

        assertEquals(expectedPositionRatings, actualPositionMap);
    }

    @Test
    void testGetMainPositionsAsString() {
        PlayerPositions positions = new PlayerPositions();
        positions.setLm(80);
        positions.setRw(85);
        positions.setRm(85);
        positions.setLw(80);
        positions.setRb(70);
        positions.setLb(70);
        positions.setCm(75);
        positions.setCb(90);
        positions.setCdm(80);
        positions.setCam(85);
        positions.setSt(90);
        positions.setGk(65);

        String mainPositions = service.getMainPositionsAsString(positions);

        assertTrue(mainPositions.contains("st"));
        assertTrue(mainPositions.contains("cb"));
        assertFalse(mainPositions.contains("cam"));
        assertFalse(mainPositions.contains("rw"));
        assertFalse(mainPositions.contains("lm"));
    }

    @Test
    void testCalculateOverallRating() {
        PlayerPositions positions = new PlayerPositions();
        positions.setLm(80);
        positions.setRw(85);
        positions.setRm(85);
        positions.setLw(80);
        positions.setRb(70);
        positions.setLb(70);
        positions.setCm(75);
        positions.setCb(90);
        positions.setCdm(80);
        positions.setCam(85);
        positions.setSt(90);
        positions.setGk(65);

        int overall = service.calculateOverallRating(positions);

        assertEquals(90, overall);
    }

    @Test
    void testBuildMainAttributes() {
        PlayerAttributes attributes = new PlayerAttributes();

        for (var category : com.buddy.football.player.calculator.PlayerAttributeWeights.AttributeCategory.values()) {
            when(attributeCalculator.calculateAttribute(category, attributes)).thenReturn(10);
        }

        PlayerMainAttributes main = service.buildMainAttributes(attributes);

        assertEquals(10, main.getDribbling());
        assertEquals(10, main.getPace());
        assertEquals(10, main.getShooting());
        assertEquals(10, main.getDefending());
        assertEquals(10, main.getGoalkeeping());
        assertEquals(10, main.getPassing());
        assertEquals(10, main.getPhysical());
    }

    @Test
    void testBuildPlayerCalculations() {
        PlayerAttributes attributes = new PlayerAttributes();

        when(metricCalculator.calculateXG(attributes)).thenReturn(1.0);
        when(metricCalculator.calculateXA(attributes)).thenReturn(2.0);
        when(metricCalculator.calculateXT(attributes)).thenReturn(3.0);
        when(metricCalculator.calculateSuccessfulDribbles(attributes)).thenReturn(4.0);
        when(metricCalculator.calculateProgressivePasses(attributes)).thenReturn(5.0);
        when(metricCalculator.calculateTackleSuccess(attributes)).thenReturn(6.0);
        when(metricCalculator.calculateInterceptions(attributes)).thenReturn(7.0);
        when(metricCalculator.calculateClearances(attributes)).thenReturn(8.0);
        when(metricCalculator.calculatePressureRecovery(attributes)).thenReturn(9.0);

        PlayerCalculations calculations = service.buildPlayerCalculations(attributes);

        assertEquals(1.0, calculations.metrics().get("xG"));
        assertEquals(2.0, calculations.metrics().get("xA"));
        assertEquals(3.0, calculations.metrics().get("xT"));
        assertEquals(4.0, calculations.metrics().get("successfulDribbles"));
        assertEquals(5.0, calculations.metrics().get("progressivePasses"));
        assertEquals(6.0, calculations.metrics().get("tackleSuccess"));
        assertEquals(7.0, calculations.metrics().get("interceptions"));
        assertEquals(8.0, calculations.metrics().get("clearances"));
        assertEquals(9.0, calculations.metrics().get("pressureRecovery"));
    }
}
