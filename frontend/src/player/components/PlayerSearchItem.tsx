import React from 'react';
import { Player } from '@/player/types/Player';
import Image from '@/ui/Image';
import { Link } from 'react-router-dom';

interface PlayerSearchItemProps {
  player: Player;
}

const PlayerSearchItem: React.FC<PlayerSearchItemProps> = ({ player }) => {
  return (
   <div className="flex justify-between items-center bg-grey-900 rounded p-2 m-b-1">
      <Link 
        to={`/players/${player.id}`} 
        className='flex items-center gap-3 p-r-8 cursor'>
        <Image src={player.nation.flagUrl} alt={player.nation.name} className="w-3" />
        
        <div className="text-sm text-white">
            {player.firstName}{' '}
            {player.lastName}
        </div>
      </Link>

      <Link
        to={`/teams/${player.team.id}`}
        className='flex items-center gap-3 p-l-8 cursor'>
        <div className="text-sm text-white">
            {player.team.name}
        </div>

        <Image src={player.team.logoUrl} alt={player.team.name} className="w-3" />
      </Link>
    </div>
  );
};

export default PlayerSearchItem;