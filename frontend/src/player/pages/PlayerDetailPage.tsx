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

function PlayerDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { player, loading: playerLoading, error: playerError } = usePlayer(id);
  const { similarPlayers, loading: similarLoading, error: similarError } = useSimilarPlayers(id);

  if (playerLoading) {
    return <Loader />;
  }

  if (playerError) {
    return <div>Error: {playerError}</div>;
  }

  if (!player) {
    return <div>Player not found.</div>;
  }

  return (
    <>
        <Card>
            <CardHeader>
                <PlayerHeader player={player} />
            </CardHeader>

            <CardContent className='p-y-4'>
                <div className='flex flex-row justify-between'>
                    <div className='m-t-8'>
                        <PlayerRadar player={player} />
                    </div>

                    <div className='m-t-8'>
                        <PlayerPositions positions={player.positions} />
                    </div>
                </div>

            </CardContent>
        </Card>

        <PlayerAttributes player={player} />

          <PlayerSimilars
            players={similarPlayers} 
            loading={similarLoading} 
            error={similarError} 
        />
    </>
  );
}

export default PlayerDetailPage;