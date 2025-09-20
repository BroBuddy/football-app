import { useState, useEffect } from 'react';
import { fetchTeamById } from '../services/TeamService';
import { Team } from '../types/Team';

export const useTeams = (id: string | undefined) => {
  const [team, setTeam] = useState<Team | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getTeam = async () => {
      if (!id) {
        setLoading(false);
        return;
      }
      try {
        const teamData = await fetchTeamById(id);
        setTeam(teamData);
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

    getTeam();
  }, [id]);

  return { team, loading, error };
};