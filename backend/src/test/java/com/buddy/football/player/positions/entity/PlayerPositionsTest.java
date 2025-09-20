package com.buddy.football.player.positions.entity;

import com.buddy.football.player.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerPositionsTest {

    private Player commonPlayer;
    private UUID commonId;
    private LocalDateTime commonNow;
    private PlayerPositions pos1;
    private PlayerPositions pos2;
    private PlayerPositions posDifferentValues;

    @BeforeEach
    void setUp() {
        commonPlayer = new Player();
        commonId = UUID.randomUUID();
        commonNow = LocalDateTime.now();

        pos1 = new PlayerPositions(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        pos1.setId(commonId);
        pos1.setPlayer(commonPlayer);
        pos1.setCreated(commonNow);
        pos1.setUpdated(commonNow);

        pos2 = new PlayerPositions(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        pos2.setId(commonId);
        pos2.setPlayer(commonPlayer);
        pos2.setCreated(commonNow);
        pos2.setUpdated(commonNow);

        posDifferentValues = new PlayerPositions(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        posDifferentValues.setId(UUID.randomUUID());
        posDifferentValues.setPlayer(new Player());
    }

    @Test
    void testConstructorAndGetters() {
        PlayerPositions positions = new PlayerPositions(10,20,30,40,50,60,70,80,90,100,110,120);

        assertThat(positions.getLm()).isEqualTo(10);
        assertThat(positions.getRw()).isEqualTo(20);
        assertThat(positions.getRm()).isEqualTo(30);
        assertThat(positions.getLw()).isEqualTo(40);
        assertThat(positions.getRb()).isEqualTo(50);
        assertThat(positions.getLb()).isEqualTo(60);
        assertThat(positions.getCm()).isEqualTo(70);
        assertThat(positions.getCb()).isEqualTo(80);
        assertThat(positions.getCdm()).isEqualTo(90);
        assertThat(positions.getCam()).isEqualTo(100);
        assertThat(positions.getSt()).isEqualTo(110);
        assertThat(positions.getGk()).isEqualTo(120);
    }

    @Test
    void testGetPositionValue() {
        PlayerPositions positions = new PlayerPositions(1,2,3,4,5,6,7,8,9,10,11,12);

        assertThat(positions.getPositionValue("lm")).isEqualTo(1);
        assertThat(positions.getPositionValue("rw")).isEqualTo(2);
        assertThat(positions.getPositionValue("rm")).isEqualTo(3);
        assertThat(positions.getPositionValue("lw")).isEqualTo(4);
        assertThat(positions.getPositionValue("rb")).isEqualTo(5);
        assertThat(positions.getPositionValue("lb")).isEqualTo(6);
        assertThat(positions.getPositionValue("cm")).isEqualTo(7);
        assertThat(positions.getPositionValue("cb")).isEqualTo(8);
        assertThat(positions.getPositionValue("cdm")).isEqualTo(9);
        assertThat(positions.getPositionValue("cam")).isEqualTo(10);
        assertThat(positions.getPositionValue("st")).isEqualTo(11);
        assertThat(positions.getPositionValue("gk")).isEqualTo(12);
        assertThat(positions.getPositionValue("unknown")).isEqualTo(0);
    }

    @Test
    void testEqualsAndHashCodeWithSameValues() {
        assertThat(pos1).isEqualTo(pos2);
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentValues() {
        assertThat(pos1).isNotEqualTo(posDifferentValues);
        assertThat(pos1.hashCode()).isNotEqualTo(posDifferentValues.hashCode());
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        assertThat(pos1).isNotEqualTo(null);

        assertThat(pos1).isNotEqualTo(new Player());
    }

    @Test
    void testEqualsWithItself() {
        assertThat(pos1).isEqualTo(pos1);
    }

    @Test
    void testToStringContainsFields() {
        PlayerPositions pos = new PlayerPositions(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        pos.setId(UUID.randomUUID());
        pos.setCreated(LocalDateTime.now());
        pos.setUpdated(LocalDateTime.now());

        String str = pos.toString();

        assertThat(str).contains("PlayerPositions{");
        assertThat(str).contains("lm=1");
        assertThat(str).contains("rw=2");
        assertThat(str).contains("st=11");
        assertThat(str).contains("gk=12");
    }
}
