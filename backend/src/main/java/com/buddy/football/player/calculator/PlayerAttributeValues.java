package com.buddy.football.player.calculator;

import com.buddy.football.player.attributes.entity.PlayerAttributes;
import org.springframework.stereotype.Component;

@Component
public class PlayerAttributeValues {

    public int getAttributeValue(PlayerAttributes attributes, String key) {
        return switch (key) {
            case "finishing" -> attributes.getAttackingAttributes().getFinishing();
            case "volleys" -> attributes.getAttackingAttributes().getVolleys();
            case "shortPassing" -> attributes.getAttackingAttributes().getShortPassing();
            case "crossing" -> attributes.getAttackingAttributes().getCrossing();
            case "headingAccuracy" -> attributes.getAttackingAttributes().getHeadingAccuracy();

            case "longPassing" -> attributes.getSkillAttributes().getLongPassing();
            case "curve" -> attributes.getSkillAttributes().getCurve();
            case "ballControl" -> attributes.getSkillAttributes().getBallControl();
            case "freeKickAccuracy" -> attributes.getSkillAttributes().getFreeKickAccuracy();
            case "dribbling" -> attributes.getSkillAttributes().getDribbling();

            case "acceleration" -> attributes.getMovementAttributes().getAcceleration();
            case "sprintSpeed" -> attributes.getMovementAttributes().getSprintSpeed();
            case "agility" -> attributes.getMovementAttributes().getAgility();
            case "balance" -> attributes.getMovementAttributes().getBalance();
            case "reactions" -> attributes.getMovementAttributes().getReactions();

            case "jumping" -> attributes.getPowerAttributes().getJumping();
            case "stamina" -> attributes.getPowerAttributes().getStamina();
            case "strength" -> attributes.getPowerAttributes().getStrength();
            case "shotPower" -> attributes.getPowerAttributes().getShotPower();
            case "longShots" -> attributes.getPowerAttributes().getLongShots();

            case "vision" -> attributes.getMentalityAttributes().getVision();
            case "composure" -> attributes.getMentalityAttributes().getComposure();
            case "attPosition" -> attributes.getMentalityAttributes().getAttPosition();
            case "penalties" -> attributes.getMentalityAttributes().getPenalties();
            case "interceptions" -> attributes.getMentalityAttributes().getInterceptions();
            case "aggression" -> attributes.getMentalityAttributes().getAggression();

            case "defensiveAwareness" -> attributes.getDefendingAttributes().getDefensiveAwareness();
            case "standingTackle" -> attributes.getDefendingAttributes().getStandingTackle();
            case "slidingTackle" -> attributes.getDefendingAttributes().getSlidingTackle();

            case "gkDiving" -> attributes.getGoalkeepingAttributes().getGkDiving();
            case "gkHandling" -> attributes.getGoalkeepingAttributes().getGkHandling();
            case "gkKicking" -> attributes.getGoalkeepingAttributes().getGkKicking();
            case "gkPositioning" -> attributes.getGoalkeepingAttributes().getGkPositioning();
            case "gkReflexes" -> attributes.getGoalkeepingAttributes().getGkReflexes();

            default -> throw new IllegalArgumentException("Unknown attribute key: " + key);
        };
    }
}
