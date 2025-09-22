import { PlayerAttributes } from "../types/PlayerAttributes";

interface FlatAttributes {
  [key: string]: number;
}

type AttributeSorted = [string, number];

const getStrengthAttributes = (attributes: PlayerAttributes, isGoalkeeper: boolean): FlatAttributes => {
    const relevantAttributes: FlatAttributes = {};
    
    if (isGoalkeeper) {
        Object.assign(relevantAttributes, attributes.goalkeepingAttributes);
    } else {
        Object.assign(relevantAttributes, attributes.attackingAttributes);
        Object.assign(relevantAttributes, attributes.skillAttributes);
        Object.assign(relevantAttributes, attributes.powerAttributes);
        Object.assign(relevantAttributes, attributes.mentalityAttributes);
        Object.assign(relevantAttributes, attributes.movementAttributes);
        Object.assign(relevantAttributes, attributes.defendingAttributes);
    }

    return relevantAttributes;
};

const getWeaknessAttributes = (attributes: PlayerAttributes): FlatAttributes => {
    const relevantAttributes: FlatAttributes = {};

    Object.assign(relevantAttributes, attributes.attackingAttributes);
    Object.assign(relevantAttributes, attributes.skillAttributes);
    Object.assign(relevantAttributes, attributes.powerAttributes);
    Object.assign(relevantAttributes, attributes.mentalityAttributes);
    Object.assign(relevantAttributes, attributes.movementAttributes);
    Object.assign(relevantAttributes, attributes.defendingAttributes);

    return relevantAttributes;
};

export const getStrengthsAndWeaknesses = (attributes: PlayerAttributes, isGoalkeeper: boolean) => {
    const strengthAttributes = getStrengthAttributes(attributes, isGoalkeeper);
    const weaknessAttributes = getWeaknessAttributes(attributes);

    const strengthArray = Object.entries(strengthAttributes) as AttributeSorted[];
    const sortedStrengths = [...strengthArray].sort((a, b) => b[1] - a[1]);
    const strengths = sortedStrengths.slice(0, 5);

    const weaknessArray = Object.entries(weaknessAttributes) as AttributeSorted[];
    const sortedWeaknesses = [...weaknessArray].sort((a, b) => a[1] - b[1]);
    const weaknesses = sortedWeaknesses.slice(0, 5);

    return { strengths, weaknesses };
};