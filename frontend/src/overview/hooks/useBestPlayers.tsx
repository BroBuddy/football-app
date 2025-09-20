import { useState, useEffect } from 'react';
import { BestPlayers } from '../services/types/BestPlayers';
import { fetchBestPlayersByAttribute } from '../services/OverviewService';

interface BestPlayersState {
  data: BestPlayers | null;
  loading: boolean;
  error: string | null;
}

export const useBestPlayers = (): BestPlayersState => {
  const [state, setState] = useState<BestPlayersState>({
    data: null,
    loading: true,
    error: null,
  });

  useEffect(() => {
    const getBestPlayers = async () => {
      try {
        setState({ data: null, loading: true, error: null });
        const bestPlayers = await fetchBestPlayersByAttribute();
        setState({ data: bestPlayers, loading: false, error: null });
      } catch (error) {
        console.log(error);
        setState({ data: null, loading: false, error: 'Failed to fetch best players.' });
      }
    };

    getBestPlayers();
  }, []);

  return state;
};