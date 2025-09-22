import { Team } from '../types/Team';
import Image from '@/ui/Image';
import { TrendingUp, User } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';
import Badge from '@/ui/Badge';

interface TeamHeaderProps {
  team: Team;
}

const TeamHeader: React.FC<TeamHeaderProps> = ({ team }) => {
  const totalPlayers = team.startingPlayers.length + team.restingPlayers.length;

  return (
    <>
      <div className="flex items-center justify-between">
        <div className='flex items-center'>
          <Image
            src={team.logoUrl}
            alt={team.name}
            className='w-3 m-r-2'
          />
          <h3>
            {team.name}
          </h3>
        </div>

        <div className='flex gap-4'>
          <Badge className='flex items-center'>
            <User size={15} className="m-r-1" />
            <span className="text-md">
              {totalPlayers} players
            </span>
          </Badge>

          <Badge className='flex items-center'>
            <TrendingUp size={15} className="m-r-1" />
            <span className="text-md">
              {formatMarketValue(team.marketValue)} total
            </span>
          </Badge>
        </div>
      </div>
    </>
  );
};

export default TeamHeader;