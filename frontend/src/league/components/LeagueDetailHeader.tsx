import { LeagueDetail } from '../types/League';
import Image from '@/ui/Image';
import { Users, TrendingUp } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';
import Badge from '@/ui/Badge';

interface LeagueDetailHeaderProps {
  league: LeagueDetail;
}

const LeagueDetailHeader: React.FC<LeagueDetailHeaderProps> = ({ league }) => {
  const leagueTeams = league.teams;
  const totalMarketValue = leagueTeams.reduce((sum, team) => sum + team.marketValue, 0);


  return (

    <>
      <div className="flex items-center justify-between">
        <div className='flex items-center'>
          <Image
            src={league.nation.flagUrl}
            alt={league.nation.name}
            className='w-3 m-r-2'
          />
          <h3>
            {league.name}
          </h3>
        </div>

        <div className="flex gap-4">
          <Badge className='flex items-center'>
            <Users size={15} className="m-r-1" />
            <span className="text-md">
              {league.teams.length} teams
            </span>
          </Badge>

          <Badge className='flex items-center'>
            <TrendingUp size={15} className="m-r-1" />
            <span className="text-md">
              {formatMarketValue(totalMarketValue)} total
            </span>
          </Badge>
        </div>
      </div>
    </>
  );
};

export default LeagueDetailHeader;