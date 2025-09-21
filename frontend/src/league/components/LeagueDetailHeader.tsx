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
  const leagueTeams = league.teams;
  const totalMarketValue = leagueTeams.reduce((sum, team) => sum + team.marketValue, 0);

  const estimatedSimulateTime = () => {
    const amountTeams = leagueTeams.length;
    const totalMatches = amountTeams * (amountTeams - 1) * 2;
    const seconds = Math.round(totalMatches / 10);

    return `up to ${seconds}s`;
  }

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

          <Link className='text-white'
            to={`/simulation/${league.id}`}>Simulate ({estimatedSimulateTime()})</Link>
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