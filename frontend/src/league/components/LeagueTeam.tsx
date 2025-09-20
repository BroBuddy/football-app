import { Team } from '@/team/types/Team';
import Image from '@/ui/Image';
import { formatMarketValue } from '@/utils';
import React from 'react';
import { Link } from 'react-router-dom';

interface LeagueTeamProps {
  team: Team;
}

const LeagueTeam: React.FC<LeagueTeamProps> = ({ team }) => {
  return (
    <Link
      key={team.id}
      to={`/teams/${team.id}`} 
      className="flex justify-between items-center bg-grey-900 rounded cursor p-2 m-b-1"
    >
      <div className='flex items-center'>
        <Image src={team.logoUrl} alt={team.name} className="w-3" />
        <div className="text-sm text-white p-l-4">{team.name}</div>
      </div>
      
      <div className="text-sm text-grey-200">
        {formatMarketValue(team.marketValue)}
      </div>
    </Link>
  );
};

export default LeagueTeam;