import { apiClient } from "@/utils";
import { BestPlayers } from "./types/BestPlayers";

export const fetchBestPlayersByAttribute = async (): Promise<BestPlayers> => {
  const data = await apiClient.fetch<BestPlayers>('players/best');
  return data;
};