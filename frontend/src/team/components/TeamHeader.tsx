import { Team } from '../types/Team';
import Image from '@/ui/Image';
import { TrendingUp, Users } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';

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
      </div>

      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Users size={15} className="m-r-1" />
          <span className="text-sm">
            {totalPlayers} players
          </span>
        </div>

        <div className="flex items-center text-grey-200">
          <TrendingUp size={15} className="m-r-1" />
          <span className="text-sm">
            {formatMarketValue(team.marketValue)} total
          </span>
        </div>
      </div>
    </>
  );
};

export default TeamHeader;