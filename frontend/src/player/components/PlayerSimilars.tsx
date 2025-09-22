import React from 'react';
import { Card, CardContent } from '@/ui/Card';
import Loader from '@/ui/Loader';
import { Player } from '../types/Player';
import PlayerSearchItem from './PlayerSearchItem';

interface PlayerSimilarsProps {
  players: Player[];
  loading: boolean;
  error: string | null;
}

const PlayerSimilars: React.FC<PlayerSimilarsProps> = ({ players, loading, error }) => {
  if (loading) {
    return <Loader />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <Card>
      <CardContent className='m-b-2'>
        <div className='grid'>
          <div className='col-12'>
            <h3>Similar Players</h3>

            {players.length === 0 && <p className='text-sm text-grey-200'>No similar players found.</p>}

            {players.map((player) => (
              <PlayerSearchItem key={player.id} player={player} />
            ))}
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

export default PlayerSimilars;