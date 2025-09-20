import { useState, useEffect } from 'react';
import { Player } from '../types/Player';
import { fetchSimilarPlayersById } from '../services/PlayerService';

export const useSimilarPlayers = (playerId: string | undefined) => {
  const [similarPlayers, setSimilarPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getSimilarPlayers = async () => {
      if (!playerId) {
        setLoading(false);
        return;
      }

      setLoading(true);
      setError(null);
      
      try {
        const fetchedPlayers = await fetchSimilarPlayersById(playerId);
        setSimilarPlayers(fetchedPlayers);
      } catch (err) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError('An unknown error occurred.');
        }
      } finally {
        setLoading(false);
      }
    };

    getSimilarPlayers();
  }, [playerId]);

  return { similarPlayers, loading, error };
};