import { Team } from '../types/Team';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import Badge from '@/ui/Badge';
import { TrendingUp, Users } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';

interface TeamStartingHeaderProps {
  team: Team;
}

const TeamStartingHeader: React.FC<TeamStartingHeaderProps> = ({ team }) => {
  return (
    <CardHeader>
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

        <div>
          <Badge className='flex items-center'>
            <TrendingUp className="w-2 m-r-1" />
            <span className="text-md">
              {formatMarketValue(team.marketValue)}
            </span>
          </Badge>
        </div>
      </div>
      
      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Users size={15} className="m-r-1" />
          <span className="text-sm">
            {team.startingPlayers.length} players
          </span>
        </div>
      </div>
    </CardHeader>
  );
};

export default TeamStartingHeader;