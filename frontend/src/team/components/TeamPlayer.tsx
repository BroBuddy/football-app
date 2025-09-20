import React from 'react';
import { Player } from '@/player/types/Player';
import { formatMarketValue } from '@/utils';
import Image from '@/ui/Image';
import { Link } from 'react-router-dom';
import { Calendar, MapPin } from 'lucide-react';
import OverallRating from '@/ui/OverallRating';
import { PlayerDetail } from '@/overview/services/types/BestPlayers';

interface TeamPlayerProps {
  player: Player | PlayerDetail;
}

const TeamPlayer: React.FC<TeamPlayerProps> = ({ player }) => {
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
      
      <div className='flex flex-row items-center gap-3'>
        <div className="flex items-center text-left w-10 text-grey-200">
            <MapPin size={15} className="m-r-1" />
            <span className='text-sm'>
                {player.mainPositions.toLocaleUpperCase()}
            </span>
        </div>
        <div className="flex items-center text-left w-10 text-grey-200">
            <Calendar size={15} className="m-r-1" />
            <span className='text-sm'>
                {player.age}y.o.
            </span>
        </div>

        <div className="flex items-center text-right w-5 text-grey-200">
            <span className='text-sm'>
                {formatMarketValue(player.marketValue)}
            </span>
        </div>
      </div>
    </Link>
  );
};

export default TeamPlayer;