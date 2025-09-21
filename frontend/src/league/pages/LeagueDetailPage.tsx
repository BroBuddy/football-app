import { Card, CardContent, CardHeader } from '@/ui/Card';
import { useParams } from 'react-router-dom';
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

  return (
    <Card>
        <CardHeader>
            <LeagueDetailHeader league={league} />
        </CardHeader>

        <CardContent className='p-y-4'>
            {league.teams.map((team: LeagueTeam) => (
                <LeagueTeamItem key={team.id} team={team} />
            ))}
        </CardContent>
    </Card>
  );
};

export default LeagueDetailPage;