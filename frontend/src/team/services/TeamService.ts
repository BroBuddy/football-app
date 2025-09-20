import { apiClient } from '@/utils';
import { Team } from '../types/Team';

export const fetchTeamById = async (id: string): Promise<Team> => {
  if (!id) {
    throw new Error("Team ID is required to fetch team data.");
  }
  
  const data = await apiClient.fetch<Team>(`teams/${id}`);
  
  return data;
};