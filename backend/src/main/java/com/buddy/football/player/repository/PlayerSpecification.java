package com.buddy.football.player.repository;

import com.buddy.football.player.entity.Player;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecification {

    public static Specification<Player> filterPlayers(double marketValueMax, String position, int minValue) {
        return (Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            jakarta.persistence.criteria.Join<Object, Object> positionsJoin = root.join("positions");

            Predicate marketValuePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("marketValue"), marketValueMax);
            Predicate startingPredicate = criteriaBuilder.isFalse(root.get("isStarting"));

            Predicate positionPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    positionsJoin.get(position), minValue);

            return criteriaBuilder.and(
                    marketValuePredicate,
                    startingPredicate,
                    positionPredicate
            );
        };
    }
}