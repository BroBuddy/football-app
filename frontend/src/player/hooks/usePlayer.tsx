import { useState, useEffect } from 'react';
import { fetchPlayerById } from '../services/PlayerService';
import { Player } from '../types/Player';

export const usePlayer = (id: string | undefined) => {
  const [player, setPlayer] = useState<Player | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getPlayer = async () => {
      if (!id) {
        setLoading(false);
        return;
      }
      try {
        const playerData = await fetchPlayerById(id);
        setPlayer(playerData);
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

    getPlayer();
  }, [id]);

  return { player, loading, error };
};