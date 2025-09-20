import { useEffect, useRef } from 'react';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import PlayerSearchResult from '../components/PlayerSearchResult';
import Loader from '@/ui/Loader';
import { usePlayerSearch } from '../hooks/usePlayerSearch';

const PlayerSearchPage: React.FC = () => {
  const { players, totalElements, loading, error, query, setQuery } = usePlayerSearch();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(event.target.value);
  };

  useEffect(() => {
    if (!loading && inputRef.current) {
      inputRef.current.focus();
    }
  }, [loading]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <>
      <Card>
        <CardHeader>
          <h2>Player Search</h2>
        </CardHeader>

        <CardContent className='m-y-4'>
          <input
            ref={inputRef}
            type="text"
            placeholder="Search player..."
            value={query}
            onChange={handleInputChange}
            className="mb-4 p-2 border rounded bg-black text-white"
          />
        </CardContent>
      </Card>

        {loading ? (
          <Loader />
        ) : (
          <PlayerSearchResult
              players={players}
              totalElements={totalElements}
          />
        )}
    </>
  );
};

export default PlayerSearchPage;