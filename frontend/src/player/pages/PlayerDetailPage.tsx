import { useParams } from 'react-router-dom';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import PlayerPositions from '@/player/components/PlayerPositions';
import PlayerAttributes from '../components/PlayerAttributes';
import PlayerRadar from '../components/PlayerRadar';
import PlayerHeader from '../components/PlayerHeader';
import Loader from '@/ui/Loader';
import { usePlayer } from '../hooks/usePlayer';
import { useSimilarPlayers } from '../hooks/useSimilarPlayers';
import PlayerSimilars from '../components/PlayerSimilars';
import PlayerMetrics from '../components/PlayerMetrics';
import { Tabs } from '@/ui/Tabs';

function PlayerDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { player, loading: playerLoading, error: playerError } = usePlayer(id);
  const { similarPlayers, loading: similarLoading, error: similarError } = useSimilarPlayers(id);

  if (playerLoading) {
    return <Loader />;
  }

  if (playerError) {
    return <p>Error: {playerError}</p>;
  }

  if (!player) {
    return <p>Player not found.</p>;
  }

  const teamTabs = [
    {
      title: 'Attributes',
      content: (
        <PlayerAttributes player={player} />
      ),
    },
    {
      title: 'Metrics',
      content: (
        <PlayerMetrics metrics={player.metrics} />
      ),
    },
    {
      title: 'Similar Players',
      content: (
        <PlayerSimilars
          players={similarPlayers} 
          loading={similarLoading} 
          error={similarError} 
      />
      ),
    },
  ];

  return (
    <div className='grid'>
        <div className='col-12'>
            <Card>
              <CardHeader>
                  <PlayerHeader player={player} />
              </CardHeader>

              <CardContent className='p-y-6'>
                  <div className='grid'>
                      <div className='col-12 md:col-6'>
                          <PlayerRadar player={player} />
                      </div>

                      <div className='col-12 md:col-6'>
                          <PlayerPositions positions={player.positions} />
                      </div>
                  </div>

              </CardContent>
          </Card>
        </div>

        <div className='col-12'>
          <Tabs tabs={teamTabs} />
        </div>
    </div>
  );
}

export default PlayerDetailPage;