import { LeagueList } from '../types/League';
import { CardHeader } from '@/ui/Card';
import Image from '@/ui/Image';
import { Users, TrendingUp } from 'lucide-react';
import React from 'react';

interface LeagueListHeaderProps {
  league: LeagueList;
}

const LeagueListHeader: React.FC<LeagueListHeaderProps> = ({ league }) => {

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

      </div>

      <div className="flex">
        <div className="flex items-center p-r-4 text-grey-200">
          <Users size={15} className="m-r-1" />
          <span className="text-sm">
            1
          </span>
        </div>

        <div className="flex items-center text-grey-200">
          <TrendingUp size={15} className="m-r-1" />
          <span className="text-sm">
            2
          </span>
        </div>
      </div>
    </CardHeader>
  );
};

export default LeagueListHeader;