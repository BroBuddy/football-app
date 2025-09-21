import React from 'react';
import LeagueTeamItem from './LeagueTeamItem';
import { LeagueTeam } from '../types/League';

interface LeagueTeamListProps {
  teams: LeagueTeam[];
}

const LeagueTeamList: React.FC<LeagueTeamListProps> = ({ teams }) => {
  return (
    <div className='flex flex-col items-stretch'>
      {teams.map((team: LeagueTeam) => (
        <LeagueTeamItem key={team.id} team={team} />
      ))}
    </div>
  );
};

export default LeagueTeamList;