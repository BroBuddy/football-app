import { Player } from "@/player/types/Player";
import { apiClient } from "@/utils";

export const scoutingForPlayers = async (
  marketValueMax: number,
  position: string,
  minValue: number
): Promise<Player[]> => {
  if (!marketValueMax || !position || !minValue) {
    throw new Error("All filter parameters are required.");
  }

  const endpoint = `players/scouting?marketValueMax=${marketValueMax}&position=${position}&minValue=${minValue}`;
  
  const data = await apiClient.fetch<Player[]>(endpoint);
  
  return data;
};