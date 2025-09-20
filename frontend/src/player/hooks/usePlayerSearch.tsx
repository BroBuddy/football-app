import { useState, useEffect } from 'react';
import { fetchPlayersByQuery } from '../services/PlayerService';
import { Player } from '../types/Player';
import { PlayerList } from '../types/PlayerList';

interface PlayerSearchState {
  players: Player[];
  totalElements: number;
  totalPages: number;
  loading: boolean;
  error: string | null;
  query: string;
  setQuery: (query: string) => void;
  currentPage: number;
  setCurrentPage: (page: number) => void;
}

export const usePlayerSearch = (): PlayerSearchState => {
  const [players, setPlayers] = useState<Player[]>([]);
  const [query, setQuery] = useState<string>('');
  const [debouncedQuery, setDebouncedQuery] = useState<string>('');
  const [totalElements, setTotalElements] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [currentPage, setCurrentPage] = useState<number>(0);

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedQuery(query);
      setCurrentPage(0);
    }, 500);

    return () => {
      clearTimeout(handler);
    };
  }, [query]);

  useEffect(() => {
    const fetchPlayers = async () => {
      setLoading(true);
      setError(null);
      
      try {
        const data: PlayerList | null = await fetchPlayersByQuery(debouncedQuery, currentPage);
        if (data) {
          setPlayers(data.content);
          setTotalElements(data.totalElements);
          setTotalPages(data.totalPages);
        } else {
          setPlayers([]);
          setTotalElements(0);
          setTotalPages(0);
        }
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

    if (debouncedQuery.length >= 3 || debouncedQuery.length === 0) {
      fetchPlayers();
    }
  }, [debouncedQuery, currentPage]);

  return { players, totalElements, totalPages, loading, error, query, setQuery, currentPage, setCurrentPage };
};