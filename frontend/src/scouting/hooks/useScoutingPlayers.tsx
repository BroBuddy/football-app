import { useState } from 'react';
import { Player } from '@/player/types/Player';
import { scoutingForPlayers } from '../services/ScoutingService';

export const useScoutingPlayers = () => {
  const [players, setPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const scoutPlayers = async (marketValueMax: number, position: string, minValue: number) => {
    setLoading(true);
    setError(null);
    
    try {
      const fetchedPlayers = await scoutingForPlayers(marketValueMax, position, minValue);
      setPlayers(fetchedPlayers);
    } catch (err) {
      setError("Failed to fetch players. Please check your input and try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return { players, loading, error, scoutPlayers };
};