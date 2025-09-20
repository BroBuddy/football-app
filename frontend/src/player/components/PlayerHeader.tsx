import { Link } from 'react-router-dom';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import OverallRating from '@/ui/OverallRating';
import { Calendar, PersonStanding } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';
import { Player } from '../types/Player';

interface PlayerHeaderProps {
  player: Player;
}

const PlayerHeader: React.FC<PlayerHeaderProps> = ({ player }) => {
  return (
    <CardHeader>
      <div className="flex items-center justify-between">
        <div className='flex items-center'>
          <Image
            src={player.nation.flagUrl}
            alt={player.lastName}
            className='w-3 m-r-2'
          />

          <h2 className='m-r-2'>
            {player.firstName} {player.lastName}
          </h2>
          <OverallRating rating={player.overallRating} />
        </div>

        <div className='flex items-center'>
          <h3>
            {player.team.name}
          </h3>
          
          <Link to={`/teams/${player.team.id}`}>
            <Image
              src={player.team.logoUrl}
              alt={player.team.name}
              className='w-3 m-l-2'
            />
          </Link>
        </div>
      </div>

      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Calendar size={15} className="m-r-1" />
          <span className='text-sm'>
            {player.age}y.o.
          </span>
        </div>

        <div className="flex items-center p-r-4 text-grey-200">
          <PersonStanding size={15} className="m-r-1" />
          <span className='text-sm'>
            {player.height}cm, {' '}
            {player.weight}kg
          </span>
        </div>

        <div className="flex items-center p-r-4 text-grey-200">
          <span className="text-sm">
            {formatMarketValue(player.marketValue)}
          </span>
        </div>
      </div>
    </CardHeader>
  );
};

export default PlayerHeader;