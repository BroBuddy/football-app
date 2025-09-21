import { Card, CardContent, CardHeader } from '@/ui/Card';
import PlayerSearchResult from '../components/PlayerSearchResult';
import Loader from '@/ui/Loader';
import { usePlayerSearch } from '../hooks/usePlayerSearch';
import Pagination from '@/ui/Pagination';
import PlayerSearchInput from '../components/PlayerSearchInput';
import { getPlayerCountText } from '@/utils';

const PlayerListPage: React.FC = () => {
  const { players, totalElements, totalPages, loading, error, query, setQuery, currentPage, setCurrentPage } = usePlayerSearch();
  const showPagination = totalElements > 20;
  
  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className='grid'>
      <div className='col-12'>
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
      </div>

      <div className='col-12'>
        {loading ? (
          <Loader />
        ) : (
            <Card>
              <CardHeader>
                <h2>{getPlayerCountText(totalElements)}</h2>
                
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
      </div>
    </div>
  );
};

export default PlayerListPage;