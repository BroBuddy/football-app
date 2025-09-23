import { Card, CardContent, CardHeader } from '@/ui/Card';
import { Link, useParams } from 'react-router-dom';
import { useLeagueDetail } from '../hooks/useLeagueDetail';
import LeagueTeamItem from '../components/LeagueTeamItem';
import Loader from '@/ui/Loader';
import { LeagueTeam } from '../types/League';
import LeagueDetailHeader from '../components/LeagueDetailHeader';

const LeagueDetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const { league, isLoading, error } = useLeagueDetail(id);

  if (isLoading) {
    return <Loader />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }
  
    if (!league) {
        return <p>League not found.</p>;
    }


  const totalGames = () => {
    const amountTeams = league.teams.length;
    const totalMatches = amountTeams * (amountTeams - 1) * 2;

    return totalMatches;
  }

  return (
    <div className='grid'>
      <div className='col-12'>
        <Card>
          <CardHeader>
            <LeagueDetailHeader league={league} />
          </CardHeader>
        </Card>
      </div>

      <div className='col-12'>
        <Card>
          <CardHeader className='flex items-center justify-between'>
            <h3>{league.teams.length} Teams</h3>

            <Link className='text-white'
              to={`/leagues/${league.id}/simulate`}>Simulate {totalGames()} Games</Link>
          </CardHeader>

          <CardContent className="m-b-2">
                {league.teams.map((team: LeagueTeam) => (
                    <LeagueTeamItem key={team.id} team={team} />
                ))}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default LeagueDetailPage;