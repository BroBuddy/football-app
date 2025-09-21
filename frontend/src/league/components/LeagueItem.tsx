import React from 'react';
import Image from '@/ui/Image';
import { Link } from 'react-router-dom';
import { LeagueList } from '../types/League';
import { formatMarketValue } from '@/utils';

interface LeagueItemProps {
  league: LeagueList;
}

const LeagueItem: React.FC<LeagueItemProps> = ({ league }) => {
  return (
   <Link
      key={league.id}
      to={`/leagues/${league.id}`} 
      className="flex justify-between items-center bg-grey-900 rounded cursor p-2 m-b-1"
    >
      <div className='flex items-center'>
        <Image src={league.nation.flagUrl} alt={league.nation.name} className="w-3" />
        <div className="text-sm text-white p-l-4">
            {league.name}
        </div>
      </div>
      
        <span className='text-sm text-grey-200'>
            {formatMarketValue(league.marketValue)}
        </span>
    </Link>
  );
};

export default LeagueItem;