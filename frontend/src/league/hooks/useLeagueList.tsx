import { useEffect, useState } from 'react';
import { fetchLeagues } from '../services/LeagueService';
import type { LeagueList } from '../types/League';

export const useLeagueList = () => {
  const [leagues, setLeagues] = useState<LeagueList[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getLeagues = async () => {
      try {
        const fetchedLeagues = await fetchLeagues();
        setLeagues(fetchedLeagues);
      } catch (err) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError('An unknown error occurred.');
        }
      } finally {
        setIsLoading(false);
      }
    };

    getLeagues();
  }, []);

  return { leagues, isLoading, error };
};