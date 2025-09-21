import { apiClient } from "@/utils";
import { LeagueDetail, LeagueList } from "../types/League";

export const fetchLeagues = async (): Promise<LeagueList[]> => {
  const controller = new AbortController();
  const signal = controller.signal;

  try {
    const data = await apiClient.fetch<LeagueList[]>('leagues', { signal });
    return data;
  } catch (error) {
    if (error instanceof Error && error.name === 'AbortError') {
      throw error;
    }

    throw error;
  }
};

export const fetchLeagueById = async (id: string): Promise<LeagueDetail> => {
  const controller = new AbortController();
  const signal = controller.signal;

  try {
    const data = await apiClient.fetch<LeagueDetail>(`leagues/${id}`, { signal });
    return data;
  } catch (error) {
    if (error instanceof Error && error.name === 'AbortError') {
      throw error;
    }

    throw error;
  }
};