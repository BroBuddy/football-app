package com.buddy.football.team.entity;

import com.buddy.football.league.entity.League;
import com.buddy.football.player.entity.Player;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "market_value")
    private Double marketValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    @JsonBackReference
    private League league;

    @OneToMany(mappedBy = "team", cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST
    })
    @JsonManagedReference
    private List<Player> players;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Team() {}

    public Team(String name, String logoUrl, Double marketValue, League league, List<Player> players, LocalDateTime created, LocalDateTime updated) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.marketValue = marketValue;
        this.league = league;
        this.players = players;
        this.created = created;
        this.updated = updated;
    }

    public Team(String name) {
        this(name, null, 0.0, null, null, null, null);
    }

    public Double getMarketValue() {
        return this.marketValue != null ? this.marketValue : 0.0;
    }

    @Transient
    public double getPlayersAverageAge() {
        if (players == null || players.isEmpty()) {
            return 0.0;
        }

        double avgAge = players.stream()
                .mapToDouble(player ->
                        Period.between(player.getBirthDate(), LocalDate.now()).getYears()
                )
                .average()
                .orElse(0.0);

        return Math.round(avgAge * 10.0) / 10.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id != null && id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", marketValue=" + marketValue +
                '}';
    }
}
