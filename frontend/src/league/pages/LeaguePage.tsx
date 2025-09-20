import LeagueList from '../components/LeagueList';
import Loader from '@/ui/Loader';
import { useLeagues } from '../hooks/useLeagues';

const LeaguePage: React.FC = () => {
  const { leagues, isLoading, error } = useLeagues();

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
    <>
      {leagues && <LeagueList leagues={leagues} />}
    </>
  );
};

export default LeaguePage;