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

        Mockito.when(mockValues.getAttributeValue(Mockito.any(), Mockito.anyString())).thenReturn(1);

        Map<String, Integer> result = calc.extractAttributes(attrs);

        assertEquals(7, result.size());
        result.values().forEach(value -> assertTrue(value > 0));
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
