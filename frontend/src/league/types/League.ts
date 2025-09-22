import type { Nation } from "../../types/Nation";

interface LeagueList {
  id: string;
  name: string;
  nation: Nation;
  marketValue: number;
}

interface LeagueDetail {
  id: string;
  name: string;
  nation: Nation;
  teams: LeagueTeam[]
}

interface LeagueTeam {
  id: string;
  name: string;
  logoUrl: string;
  marketValue: number;
  playerCount: number;
}

export type { LeagueList, LeagueDetail, LeagueTeam }