import { Team } from "@/team/types/Team";
import type { Nation } from "../../types/Nation";

interface League {
  id: string;
  name: string;
  nation: Nation;
  teams: Team[];
}

export type { League }