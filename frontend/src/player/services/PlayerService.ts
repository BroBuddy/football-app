import { apiClient } from '@/utils';
import { Player } from '../types/Player';
import { PlayerList } from '../types/PlayerList';

export const fetchPlayerById = async (id: string): Promise<Player> => {
  if (!id) {
    throw new Error("Player ID is required to fetch player data.");
  }
  
  const data = await apiClient.fetch<Player>(`players/${id}`);
  
  return data;
};

export const fetchPlayersByQuery = async (query: string): Promise<PlayerList> => {
  const endpoint = query ? `players?query=${query}` : 'players';

  const data = await apiClient.fetch<PlayerList>(endpoint);
  
  return data;
};

export const fetchSimilarPlayersById = async (id: string): Promise<Player[]> => {
  if (!id) {
    throw new Error("Player ID is required to fetch similar players.");
  }

  const data = await apiClient.fetch<Player[]>(`players/${id}/similar`);

  return data;
};