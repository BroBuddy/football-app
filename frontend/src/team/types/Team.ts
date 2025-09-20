import { Player } from "@/player/types/Player";

export interface Team {
  id: string;
  name: string;
  logoUrl: string;
  marketValue: number;
  startingPlayers: Player[];
  restingPlayers: Player[];
}
