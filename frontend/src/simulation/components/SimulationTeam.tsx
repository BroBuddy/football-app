import React from 'react';
import { Link } from 'react-router-dom';
import { TableRow } from '../types/TableList';

interface SimulationTeamProps {
  row: TableRow;
}

const SimulationTeam: React.FC<SimulationTeamProps> = ({ row }) => {
  return (
    <Link
      key={row.id}
      to={`/teams/${row.id}`} 
      className="flex justify-between items-center bg-grey-900 rounded cursor p-2 m-b-1"
    >
      <div className='flex items-center text-sm'>
        <div className="text-grey-300 p-l-4">{row.rank}</div>
        <div className="text-white p-l-4">{row.name}</div>
      </div>
      
      <div className='flex flex-row items-center gap-3 text-sm'>
        <div className="flex items-center text-left w-10 text-grey-200">
          {row.won} | {row.draw} | {row.lose}
        </div>

        <div className="flex items-center text-left w-10 text-grey-200">
          {row.goals}
        </div>

        <div className="flex items-center text-left w-10 text-grey-200">
          {row.diff}
        </div>

        <div className="flex items-center text-left w-4 text-grey-200">
          {row.points}pts
        </div>
      </div>
    </Link>
  );
};

export default SimulationTeam;