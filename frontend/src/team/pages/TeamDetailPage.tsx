import { useParams } from 'react-router-dom';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import TeamHeader from '../components/TeamHeader';
import TeamPlayerList from '../components/TeamPlayerList';
import Loader from '@/ui/Loader';
import { useTeams } from '../hooks/useTeams';
import { Tabs } from '@/ui/Tabs';

function TeamDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { team, loading, error } = useTeams(id);

  if (loading) {
    return <Loader />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!team) {
    return <div>Team not found.</div>;
  }

  const teamTabs = [
    {
      title: 'Starting Players',
      content: (
        <Card>
          <CardHeader>
            <h3>{team.startingPlayers.length} Starting Players</h3>
          </CardHeader>

          <CardContent className="m-b-2">
            <TeamPlayerList players={team.startingPlayers} />
          </CardContent>
        </Card>
      ),
    },
    {
      title: 'Resting Players',
      content: (
        <Card>
          <CardHeader>
            <h3>{team.restingPlayers.length} Resting Players</h3>
          </CardHeader>

          <CardContent className="m-b-2">          
            <TeamPlayerList players={team.restingPlayers} />
          </CardContent>
        </Card>
      ),
    },
  ];

  return (
    <div className='grid'>
      <div className='col-12'>
        <Card>
          <CardHeader>
            <TeamHeader team={team} />
          </CardHeader>
        </Card>
      </div>

      <div className='col-12'>
        <Tabs tabs={teamTabs} />
      </div>
    </div>
  );
}

export default TeamDetailPage;