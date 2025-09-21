import React from 'react';
import { Player } from '@/player/types/Player';
import Image from '@/ui/Image';
import { Link } from 'react-router-dom';
import OverallRating from '@/ui/OverallRating';
import { PlayerDetail } from '@/overview/services/types/BestPlayers';

interface OverviewPlayerProps {
  player: Player | PlayerDetail;
}

const OverviewPlayer: React.FC<OverviewPlayerProps> = ({ player }) => {
  return (
   <Link
      key={player.id}
      to={`/players/${player.id}`} 
      className="flex justify-between items-center bg-grey-900 rounded cursor p-2 m-b-1">
      <div className='flex items-center'>
        <OverallRating rating={player.overallRating} />

        <Image src={player.nation.flagUrl} alt={player.nation.name} className="w-3 m-x-4" />
        
        <div className="text-sm text-white">
            {player.firstName}{' '}
            {player.lastName}
        </div>
      </div>
    </Link>
  );
};

export default OverviewPlayer;