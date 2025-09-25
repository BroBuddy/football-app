import { Link } from 'react-router-dom';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import OverallRating from '@/ui/OverallRating';
import { Calendar, PersonStanding } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';
import { Player } from '../types/Player';
import PlayerType from './PlayerType';
import Badge from '@/ui/Badge';

interface PlayerHeaderProps {
  player: Player;
}

const PlayerHeader: React.FC<PlayerHeaderProps> = ({ player }) => {
  const isGoalkeeper = player.mainPositions === 'gk';

  return (
    <CardHeader>
      <div className="flex items-center justify-between">
        <div className='flex items-center'>
          <OverallRating rating={player.overallRating} />

          <Image
            src={player.nation.flagUrl}
            alt={player.lastName}
            className='w-3 m-x-3'
          />

          <h2 className='m-r-2'>
            {player.firstName} {player.lastName}
          </h2>
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

      <div className="flex gap-3">
        <Badge>
          <Calendar size={15} className="m-r-1" />
          {player.age}y.o.
        </Badge>

        <Badge>
          <PersonStanding size={15} className="m-r-1" />
          {player.height}cm, {' '}
          {player.weight}kg
        </Badge>

        <Badge>
          {formatMarketValue(player.marketValue)}
        </Badge>

        {!isGoalkeeper && <PlayerType mainAttributes={player.mainAttributes} />}
      </div>
    </CardHeader>
  );
};

export default PlayerHeader;