export interface PlayerAttributes {
  attackingAttributes: AttackingAttributes;
  skillAttributes: SkillAttributes;
  movementAttributes: MovementAttributes;
  powerAttributes: PowerAttributes;
  mentalityAttributes: MentalityAttributes;
  defendingAttributes: DefendingAttributes;
  goalkeepingAttributes: GoalkeepingAttributes;
}

export interface MainAttributes {
  dribbling: number;
  pace: number;
  shooting: number;
  defending: number;
  goalkeeping?: number;
  passing: number;
  physical: number;
}

export interface AttackingAttributes {
  crossing: number;
  finishing: number;
  headingAccuracy: number;
  shortPassing: number;
  volleys: number;
}

export interface SkillAttributes {
  dribbling: number;
  curve: number;
  freeKickAccuracy: number;
  longPassing: number;
  ballControl: number;
}

export interface MovementAttributes {
  acceleration: number;
  sprintSpeed: number;
  agility: number;
  reactions: number;
  balance: number;
}

export interface PowerAttributes {
  shotPower: number;
  jumping: number;
  stamina: number;
  strength: number;
  longShots: number;
}

export interface MentalityAttributes {
  aggression: number;
  interceptions: number;
  attPosition: number;
  vision: number;
  penalties: number;
  composure: number;
}

export interface DefendingAttributes {
  defensiveAwareness: number;
  standingTackle: number;
  slidingTackle: number;
}

export interface GoalkeepingAttributes {
  gkDiving: number;
  gkHandling: number;
  gkKicking: number;
  gkPositioning: number;
  gkReflexes: number;
}