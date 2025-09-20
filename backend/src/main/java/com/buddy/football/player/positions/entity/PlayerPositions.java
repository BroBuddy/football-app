package com.buddy.football.player.positions.entity;

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
@Table(name = "player_positions")
public class PlayerPositions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "lm")
    private int lm;

    @Column(name = "rw")
    private int rw;

    @Column(name = "rm")
    private int rm;

    @Column(name = "lw")
    private int lw;

    @Column(name = "rb")
    private int rb;

    @Column(name = "lb")
    private int lb;

    @Column(name = "cm")
    private int cm;

    @Column(name = "cb")
    private int cb;

    @Column(name = "cdm")
    private int cdm;

    @Column(name = "cam")
    private int cam;

    @Column(name = "st")
    private int st;

    @Column(name = "gk")
    private int gk;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public PlayerPositions() {}

    public PlayerPositions(int lm, int rw, int rm, int lw, int rb, int lb, int cm, int cb, int cdm, int cam, int st, int gk) {
        this.lm = lm;
        this.rw = rw;
        this.rm = rm;
        this.lw = lw;
        this.rb = rb;
        this.lb = lb;
        this.cm = cm;
        this.cb = cb;
        this.cdm = cdm;
        this.cam = cam;
        this.st = st;
        this.gk = gk;
    }

    public int getPositionValue(String position) {
        return switch (position.toLowerCase()) {
            case "st" -> st;
            case "cam" -> cam;
            case "cb" -> cb;
            case "cm" -> cm;
            case "rw" -> rw;
            case "rm" -> rm;
            case "lw" -> lw;
            case "lm" -> lm;
            case "rb" -> rb;
            case "lb" -> lb;
            case "cdm" -> cdm;
            case "gk" -> gk;
            default -> 0;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPositions that = (PlayerPositions) o;
        return lm == that.lm && rw == that.rw && rm == that.rm && lw == that.lw && rb == that.rb && lb == that.lb && cm == that.cm && cb == that.cb && cdm == that.cdm && cam == that.cam && st == that.st && gk == that.gk && Objects.equals(id, that.id) && Objects.equals(player, that.player) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player, lm, rw, rm, lw, rb, lb, cm, cb, cdm, cam, st, gk, created, updated);
    }

    @Override
    public String toString() {
        return "PlayerPositions{" +
                "id=" + id +
                ", player=" + player +
                ", lm=" + lm +
                ", rw=" + rw +
                ", rm=" + rm +
                ", lw=" + lw +
                ", rb=" + rb +
                ", lb=" + lb +
                ", cm=" + cm +
                ", cb=" + cb +
                ", cdm=" + cdm +
                ", cam=" + cam +
                ", st=" + st +
                ", gk=" + gk +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
