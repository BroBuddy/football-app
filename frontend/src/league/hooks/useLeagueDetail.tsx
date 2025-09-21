import { useEffect, useState } from 'react';
import { fetchLeagueById } from '../services/LeagueService'; 
import type { LeagueDetail, LeagueTeam } from '../types/League';

export const useLeagueDetail = (id: string | undefined) => {
  const [league, setLeague] = useState<LeagueDetail | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) {
      setIsLoading(false);
      setError('League ID is required.');
      return;
    }

    const getLeague = async () => {
      try {
        const fetchedLeague = await fetchLeagueById(id);
        
        const sortedTeams = fetchedLeague.teams.sort((a: LeagueTeam, b: LeagueTeam) => {
          return b.marketValue - a.marketValue;
        });
        
        const sortedLeague = { ...fetchedLeague, teams: sortedTeams };
        
        setLeague(sortedLeague);
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

    getLeague();
  }, [id]);

  return { league, isLoading, error };
};