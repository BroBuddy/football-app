import React from 'react';
import { Player } from '../types/Player';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import { Users } from 'lucide-react';
import PlayerSearchItem from './PlayerSearchItem';

interface PlayerSearchResultProps {
  players: Player[];
  totalElements: number;
}

const PlayerSearchResult: React.FC<PlayerSearchResultProps> = ({ players, totalElements }) => {
  return (
    <Card>
      <CardHeader>
        <h2>Player Result</h2>
        
        <div className="flex">
          <div className="flex items-center p-r-4 text-gray-200">
            <Users size={15} className="m-r-1" />
            <span className="text-sm">{players.length} of {totalElements} players</span>
          </div>
        </div>
      </CardHeader>
      
      <CardContent className='m-y-4'>
        {players.length > 0 && (
          players.map((player: Player) => (
            <PlayerSearchItem key={player.id} player={player} />
          ))
        )}
      </CardContent>
    </Card>
  );
};

export default PlayerSearchResult;