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
          <PlayerAttributes player={player} />
        </div>

        <div className='col-12'>
          <PlayerMetrics metrics={player.metrics} />
        </div>
        
        <div className='col-12'>
            <PlayerSimilars
              players={similarPlayers} 
              loading={similarLoading} 
              error={similarError} 
          />
        </div>
    </div>
  );
}

export default PlayerDetailPage;