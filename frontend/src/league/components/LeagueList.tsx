import React from 'react';
import type { League } from '../types/League';
import { Card, CardHeader, CardContent } from '../../ui/Card';
import LeagueHeader from './LeagueHeader';
import LeagueTeamList from './LeagueTeamList';

interface LeagueListProps {
  leagues: League[];
}

const LeagueList: React.FC<LeagueListProps> = ({ leagues }) => {
  if (leagues.length === 0) {
    return <div>No leagues found.</div>;
  }

  return (
    <>
      {leagues.map((league) => (
        <Card key={league.id}>
          <CardHeader>
            <LeagueHeader league={league} />
          </CardHeader>
          
          <CardContent className="m-t-4">
            <LeagueTeamList teams={league.teams} />
          </CardContent>
        </Card>
      ))}
    </>
  );
};

export default LeagueList;