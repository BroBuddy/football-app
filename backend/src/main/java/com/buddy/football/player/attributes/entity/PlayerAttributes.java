package com.buddy.football.player.attributes.entity;

import com.buddy.football.player.entity.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "player_attributes")
public class PlayerAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Embedded
    private AttackingAttributes attackingAttributes;

    @Embedded
    private SkillAttributes skillAttributes;

    @Embedded
    private MovementAttributes movementAttributes;

    @Embedded
    private PowerAttributes powerAttributes;

    @Embedded
    private MentalityAttributes mentalityAttributes;

    @Embedded
    private DefendingAttributes defendingAttributes;
    
    @Embedded
    private GoalkeepingAttributes goalkeepingAttributes;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public PlayerAttributes() {}

    public PlayerAttributes(AttackingAttributes attackingAttributes, SkillAttributes skillAttributes, MovementAttributes movementAttributes, PowerAttributes powerAttributes, MentalityAttributes mentalityAttributes, DefendingAttributes defendingAttributes, GoalkeepingAttributes goalkeepingAttributes, LocalDateTime created, LocalDateTime updated) {
        this.attackingAttributes = attackingAttributes;
        this.skillAttributes = skillAttributes;
        this.movementAttributes = movementAttributes;
        this.powerAttributes = powerAttributes;
        this.mentalityAttributes = mentalityAttributes;
        this.defendingAttributes = defendingAttributes;
        this.goalkeepingAttributes = goalkeepingAttributes;
        this.created = created;
        this.updated = updated;
    }

    public PlayerAttributes(AttackingAttributes attackingAttributes, SkillAttributes skillAttributes, MovementAttributes movementAttributes, PowerAttributes powerAttributes, MentalityAttributes mentalityAttributes, DefendingAttributes defendingAttributes, GoalkeepingAttributes goalkeepingAttributes) {
        this(attackingAttributes, skillAttributes, movementAttributes, powerAttributes, mentalityAttributes, defendingAttributes, goalkeepingAttributes, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAttributes that = (PlayerAttributes) o;
        return Objects.equals(id, that.id) && Objects.equals(player, that.player) && Objects.equals(attackingAttributes, that.attackingAttributes) && Objects.equals(skillAttributes, that.skillAttributes) && Objects.equals(movementAttributes, that.movementAttributes) && Objects.equals(powerAttributes, that.powerAttributes) && Objects.equals(mentalityAttributes, that.mentalityAttributes) && Objects.equals(defendingAttributes, that.defendingAttributes) && Objects.equals(goalkeepingAttributes, that.goalkeepingAttributes) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player, attackingAttributes, skillAttributes, movementAttributes, powerAttributes, mentalityAttributes, defendingAttributes, goalkeepingAttributes, created, updated);
    }

    @Override
    public String toString() {
        return "PlayerAttributes{" +
                "id=" + id +
                ", player=" + player +
                ", attackingAttributes=" + attackingAttributes +
                ", skillAttributes=" + skillAttributes +
                ", movementAttributes=" + movementAttributes +
                ", powerAttributes=" + powerAttributes +
                ", mentalityAttributes=" + mentalityAttributes +
                ", defendingAttributes=" + defendingAttributes +
                ", goalkeepingAttributes=" + goalkeepingAttributes +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}