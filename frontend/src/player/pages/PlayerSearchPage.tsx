import { Card, CardContent, CardHeader } from '@/ui/Card';
import PlayerSearchResult from '../components/PlayerSearchResult';
import Loader from '@/ui/Loader';
import { usePlayerSearch } from '../hooks/usePlayerSearch';
import Pagination from '@/ui/Pagination';
import PlayerSearchInput from '../components/PlayerSearchInput';

const PlayerSearchPage: React.FC = () => {
  const { players, totalElements, totalPages, loading, error, query, setQuery, currentPage, setCurrentPage } = usePlayerSearch();
  const showPagination = totalElements > 20;
  
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
          <PlayerSearchInput
            query={query} 
            setQuery={setQuery} 
          />
        </CardContent>
      </Card>

      {loading ? (
        <Loader />
      ) : (
          <Card>
            <CardHeader>
              <h2>{getPlayerCountText()}</h2>
              
              {showPagination && <Pagination 
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />}
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