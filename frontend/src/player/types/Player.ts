import { Team } from "@/team/types/Team";
import { Nation } from "../../types/Nation";
import { PlayerMetrics } from "./PlayerMetrics";
import { PlayerAttributes, MainAttributes } from "./PlayerAttributes";

export interface Player {
  id: string;
  firstName: string;
  lastName: string;
  age: number;
  height: number;
  weight: number;
  marketValue: number;
  nation: Nation;
  team: Team;
  isStarting: boolean;
  mainAttributes: MainAttributes;
  attributes: PlayerAttributes;
  positions: PlayerPositions;
  mainPositions: string;
  overallRating: number;
  metrics: PlayerMetrics;
}

export interface PlayerPositions {
  [key: string]: number;
}
