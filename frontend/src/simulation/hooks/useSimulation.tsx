import { useState, useEffect } from 'react';
import { simulateLeagueById } from '../services/SimulationService';
import { TableRow } from '../types/TableList';

export const useSimulation = (id: string | undefined) => {
  const [tableData, setTableData] = useState<TableRow[] | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getLeagueData = async () => {
      if (!id) {
        setLoading(false);
        return;
      }

      setLoading(true);
      setError(null);
      
      try {
        const data = await simulateLeagueById(id);
        setTableData(data);
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

    getLeagueData();
  }, [id]);

  return { tableData, loading, error };
};