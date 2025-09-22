import Image from '@/ui/Image';
import { formatMarketValue } from '@/utils';
import { Users } from 'lucide-react';
import React from 'react';
import { Link } from 'react-router-dom';
import { LeagueTeam } from '../types/League';

interface LeagueTeamProps {
  team: LeagueTeam;
}

const LeagueTeamItem: React.FC<LeagueTeamProps> = ({ team }) => {
  return (
    <Link
      key={team.id}
      to={`/teams/${team.id}`} 
      className="flex justify-between items-center bg-grey-900 rounded cursor p-2 m-b-1"
    >
      <div className='flex items-center'>
        <Image src={team.logoUrl} alt={team.name} className="w-3" />
        <div className="text-sm text-white p-l-4">
          {team.name}
        </div>
      </div>
      
      <div className='flex flex-row items-center gap-3'>
        <div className="flex items-center text-left w-10 text-grey-200">
            <Users size={15} className="m-r-1" />
            <span className='text-sm'>
                {team.playerCount}
            </span>
        </div>

        <div className="flex items-center text-right w-6 text-grey-200">
            <span className='text-sm'>
                {formatMarketValue(team.marketValue)}
            </span>
        </div>
      </div>
    </Link>
  );
};

export default LeagueTeamItem;