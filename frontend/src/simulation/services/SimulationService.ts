import { apiClient } from "@/utils";
import { TableRow } from "../types/TableList";

export const simulateLeagueById = async (id: string): Promise<TableRow[]> => {
  if (!id) {
    throw new Error("League ID is required for simulation.");
  }
  
  const data = await apiClient.fetch<TableRow[]>(`simulation/league/${id}`);
  
  return data;
};