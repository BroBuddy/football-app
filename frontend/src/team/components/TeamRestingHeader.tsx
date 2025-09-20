import { Team } from '../types/Team';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import { Users } from 'lucide-react';
import React from 'react';

interface TeamRestingHeaderProps {
  team: Team;
}

const TeamRestingHeader: React.FC<TeamRestingHeaderProps> = ({ team }) => {
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
            Resting Players
          </h3>
        </div>
      </div>
      
      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Users size={15} className="m-r-1" />
          <span className="text-sm">
            {team.restingPlayers.length} players
          </span>
        </div>
      </div>
    </CardHeader>
  );
};

export default TeamRestingHeader;