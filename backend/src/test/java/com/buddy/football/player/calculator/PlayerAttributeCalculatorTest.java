package com.buddy.football.player.calculator;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAttributeCalculatorTest {

    @Test
    void testExtractAttributes() {
        PlayerAttributeValues mockValues = Mockito.mock(PlayerAttributeValues.class);
        PlayerAttributeCalculator calc = new PlayerAttributeCalculator(mockValues);

        PlayerAttributes attrs = new PlayerAttributes();

        Mockito.when(mockValues.getAttributeValue(Mockito.any(PlayerAttributes.class), Mockito.anyString()))
                .thenReturn(10);

        Map<String, Integer> result = calc.extractAttributes(attrs);

        assertEquals(7, result.size());

        result.values().forEach(value -> assertTrue(value > 0));
        result.values().forEach(value -> assertEquals(10, value));
    }

    @Test
    void testUnknownCategoryThrows() {
        PlayerAttributeValues mockValues = Mockito.mock(PlayerAttributeValues.class);
        PlayerAttributeCalculator calc = new PlayerAttributeCalculator(mockValues);
        PlayerAttributes attrs = new PlayerAttributes();

        assertThrows(IllegalArgumentException.class, () ->
                calc.calculateAttribute(null, attrs)
        );
    }
}
