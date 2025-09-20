package com.buddy.football.player.entity;

import com.buddy.football.nation.entity.Nation;
import com.buddy.football.player.attributes.entity.PlayerMainAttributes;
import com.buddy.football.player.attributes.entity.PlayerAttributes;
import com.buddy.football.player.positions.entity.PlayerPositions;
import com.buddy.football.team.entity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "market_value")
    private double marketValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    @Column(name = "is_starting")
    private boolean isStarting;

    @OneToOne(mappedBy = "player",cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private PlayerAttributes attributes;

    @OneToOne(mappedBy = "player",cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private PlayerPositions positions;

    @Embedded
    private PlayerMainAttributes mainAttributes;

    @Column(name = "main_positions")
    private String mainPositions;

    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Player() {}

    public Player(String firstName, String lastName, LocalDate birthDate, Integer height, Integer weight, double marketValue, Nation nation, Team team, boolean isStarting, LocalDateTime created, LocalDateTime updated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.marketValue = marketValue;
        this.nation = nation;
        this.team = team;
        this.isStarting = isStarting;
        this.created = created;
        this.updated = updated;
    }

    public Player(String firstName, String lastName) {
        this(firstName, lastName, null, 0, 0, 0.0, null, null, false, null, null);
    }

    @Transient
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(birthDate, player.birthDate) && Objects.equals(height, player.height) && Objects.equals(weight, player.weight) && Objects.equals(nation, player.nation) && Objects.equals(team, player.team) && Objects.equals(attributes, player.attributes) && Objects.equals(created, player.created) && Objects.equals(updated, player.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, height, weight, nation, team, attributes, created, updated);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", height=" + height +
                ", weight=" + weight +
                ", nation=" + nation +
                ", team=" + team +
                ", attributes=" + attributes +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
