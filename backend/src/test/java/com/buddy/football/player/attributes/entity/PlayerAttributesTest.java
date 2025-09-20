package com.buddy.football.player.attributes.entity;

import com.buddy.football.player.entity.Player;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayerAttributesTest {

    @Test
    void testSettersAndGettersWithMocks() {
        PlayerAttributes attributes = new PlayerAttributes();
        Player player = new Player();
        player.setId(UUID.randomUUID());

        // Attribute-Embeddables als Mocks
        AttackingAttributes att = mock(AttackingAttributes.class);
        SkillAttributes skill = mock(SkillAttributes.class);
        MovementAttributes movement = mock(MovementAttributes.class);
        PowerAttributes power = mock(PowerAttributes.class);
        MentalityAttributes mentality = mock(MentalityAttributes.class);
        DefendingAttributes defending = mock(DefendingAttributes.class);
        GoalkeepingAttributes goalkeeping = mock(GoalkeepingAttributes.class);

        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime updated = LocalDateTime.now();

        UUID id = UUID.randomUUID();

        attributes.setId(id);
        attributes.setPlayer(player);
        attributes.setAttackingAttributes(att);
        attributes.setSkillAttributes(skill);
        attributes.setMovementAttributes(movement);
        attributes.setPowerAttributes(power);
        attributes.setMentalityAttributes(mentality);
        attributes.setDefendingAttributes(defending);
        attributes.setGoalkeepingAttributes(goalkeeping);
        attributes.setCreated(created);
        attributes.setUpdated(updated);

        // Verifizieren
        assertEquals(id, attributes.getId());
        assertEquals(player, attributes.getPlayer());
        assertEquals(att, attributes.getAttackingAttributes());
        assertEquals(skill, attributes.getSkillAttributes());
        assertEquals(movement, attributes.getMovementAttributes());
        assertEquals(power, attributes.getPowerAttributes());
        assertEquals(mentality, attributes.getMentalityAttributes());
        assertEquals(defending, attributes.getDefendingAttributes());
        assertEquals(goalkeeping, attributes.getGoalkeepingAttributes());
        assertEquals(created, attributes.getCreated());
        assertEquals(updated, attributes.getUpdated());
    }

    @Test
    void testEqualsAndHashCode() {
        PlayerAttributes a1 = new PlayerAttributes();
        PlayerAttributes a2 = new PlayerAttributes();

        UUID sameId = UUID.randomUUID();
        a1.setId(sameId);
        a2.setId(sameId);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());

        a2.setId(UUID.randomUUID());
        assertNotEquals(a1, a2);
    }

    @Test
    void testToStringContainsClassName() {
        PlayerAttributes attributes = new PlayerAttributes();
        String str = attributes.toString();
        assertTrue(str.contains("PlayerAttributes"));
    }
}
