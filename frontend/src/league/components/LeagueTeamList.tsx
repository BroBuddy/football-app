import React from 'react';
import { Team } from '../../team/types/Team';
import LeagueTeam from './LeagueTeam';

interface LeagueTeamListProps {
  teams: Team[];
}

const LeagueTeamList: React.FC<LeagueTeamListProps> = ({ teams }) => {
  return (
    <div className='flex flex-col items-stretch'>
      {teams.map((team) => (
        <LeagueTeam key={team.id} team={team} />
      ))}
    </div>
  );
};

export default LeagueTeamList;