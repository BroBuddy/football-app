import Loader from '@/ui/Loader';
import { useLeagueList } from '../hooks/useLeagueList';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import { LeagueList } from '../types/League';
import LeagueItem from '../components/LeagueItem';

const LeagueListPage: React.FC = () => {
  const { leagues, isLoading, error } = useLeagueList();

  if (isLoading) {
    return <Loader />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (leagues.length === 0) {
    return <div>No leagues found.</div>;
  }

  return (
    <Card>
          <CardHeader>
            <h2>Leagues</h2>
          </CardHeader>
          
          <CardContent className="m-t-4">
            {leagues.map((league: LeagueList) => (
              <LeagueItem key={league.id} league={league} />
            ))}
          </CardContent>
        </Card>
  );
};

export default LeagueListPage;