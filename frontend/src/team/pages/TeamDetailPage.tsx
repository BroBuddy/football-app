import { useParams } from 'react-router-dom';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import TeamStartingHeader from '../components/TeamStartingHeader';
import TeamRestingHeader from '../components/TeamRestingHeader';
import TeamPlayerList from '../components/TeamPlayerList';
import Loader from '@/ui/Loader';
import { useTeams } from '../hooks/useTeams';

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

  return (
    <>
      <Card>
        <CardHeader>
          <TeamStartingHeader team={team} />          
        </CardHeader>
        
          <CardContent className="m-t-4">
            <TeamPlayerList players={team.startingPlayers} />
          </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <TeamRestingHeader team={team} />
        </CardHeader>
        
          <CardContent className="m-t-4">
            <TeamPlayerList players={team.restingPlayers} />
          </CardContent>
      </Card>
    </>
  );
}

export default TeamDetailPage;