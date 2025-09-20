package com.buddy.football.nation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "nations")
public class Nation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 3)
    private String code;

    @Column(name = "flag_url")
    private String flagUrl;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Nation() {}

    public Nation(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Nation(String name, String code, String flagUrl, LocalDateTime created, LocalDateTime updated) {
        this.name = name;
        this.code = code;
        this.flagUrl = flagUrl;
        this.created = created;
        this.updated = updated;
    }

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
        this.updated = now;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nation nation = (Nation) o;
        return Objects.equals(id, nation.id) && Objects.equals(name, nation.name) && Objects.equals(code, nation.code) && Objects.equals(flagUrl, nation.flagUrl) && Objects.equals(created, nation.created) && Objects.equals(updated, nation.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, flagUrl, created, updated);
    }

    @Override
    public String toString() {
        return "Nation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", flagUrl='" + flagUrl + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
