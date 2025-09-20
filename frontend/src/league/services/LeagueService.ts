import { apiClient } from "@/utils";
import { League } from "../types/League";

export const fetchLeagues = async (): Promise<League[]> => {
  const controller = new AbortController();
  const signal = controller.signal;

  try {
    const data = await apiClient.fetch<League[]>('leagues', { signal });
    return data;
  } catch (error) {
    if (error instanceof Error && error.name === 'AbortError') {
      throw error;
    }

    throw error;
  }
};