import React from 'react';
import { Card, CardContent, CardHeader } from '@/ui/Card';
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
      <CardHeader>
        <div className="flex items-center justify-between">
          <div className='flex items-center'>
            <h3>Similar Players</h3>
          </div>
        </div>
      </CardHeader>

      <CardContent className="m-y-4">
        {players.length === 0 && <p className='text-sm text-grey-200'>No similar players found.</p>}

        {players.map((player) => (
          <PlayerSearchItem key={player.id} player={player} />
        ))}
      </CardContent>
    </Card>
  );
};

export default PlayerSimilars;