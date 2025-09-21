import { LeagueDetail } from '../types/League';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import { Users, TrendingUp } from 'lucide-react';
import { formatMarketValue } from '@/utils';
import React from 'react';
import { Link } from 'react-router-dom';

interface LeagueDetailHeaderProps {
  league: LeagueDetail;
}

const LeagueDetailHeader: React.FC<LeagueDetailHeaderProps> = ({ league }) => {
  const totalMarketValue = league.teams.reduce((sum, team) => sum + team.marketValue, 0);
  const canSimulateLeague = league.teams.length >= 10;

  return (
    <CardHeader>
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

        {canSimulateLeague && <div>
          <Link className='text-white'
            to={`/simulation/${league.id}`}>Simulation</Link>
        </div>}
      </div>

      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Users size={15} className="m-r-1" />
          <span className="text-sm">
            {league.teams.length} teams
          </span>
        </div>

        <div className="flex items-center text-grey-200">
          <TrendingUp size={15} className="m-r-1" />
          <span className="text-sm">
            {formatMarketValue(totalMarketValue)} total
          </span>
        </div>
      </div>
    </CardHeader>
  );
};

export default LeagueDetailHeader;