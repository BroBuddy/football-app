import { useEffect, useRef } from 'react';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import PlayerSearchResult from '../components/PlayerSearchResult';
import Loader from '@/ui/Loader';
import { usePlayerSearch } from '../hooks/usePlayerSearch';
import Pagination from '@/ui/Pagination';

const PlayerSearchPage: React.FC = () => {
  const { players, totalElements, totalPages, loading, error, query, setQuery, currentPage, setCurrentPage } = usePlayerSearch();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(event.target.value);
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const getPlayerCountText = () => {
    if (totalElements === 0) {
      return "No Players found";
    } else if (totalElements === 1) {
      return "1 Player found";
    } else {
      return `${totalElements} Players found`;
    }
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
          <Card>
            <CardHeader>
              <h2>{getPlayerCountText()}</h2>
              
              <Pagination 
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            </CardHeader>
            
            <CardContent className='m-y-4'>
                <PlayerSearchResult
                  players={players}
                />
            </CardContent>
          </Card>
      )}
    </>
  );
};

export default PlayerSearchPage;