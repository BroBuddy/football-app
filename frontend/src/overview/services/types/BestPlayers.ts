
export interface BestPlayers {
  dribbling: PlayerDetail[];
  pace: PlayerDetail[];
  shooting: PlayerDetail[];
  defending: PlayerDetail[];
  goalkeeping: PlayerDetail[];
  passing: PlayerDetail[];
  physical: PlayerDetail[];
}

export interface PlayerDetail {
  id: string;
  firstName: string;
  lastName: string;
  age: number;
  marketValue: number;
  nation: {
    id: string;
    name: string;
    code: string;
    flagUrl: string;
  };
  team: {
    id: string;
    name: string;
    logoUrl: string;
  };
  mainPositions: string;
  overallRating: number;
}