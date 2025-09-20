import React from 'react';
import { Player } from '@/player/types/Player';
import TeamPlayer from './TeamPlayer';

interface TeamPlayerListProps {
  players: Player[];
}

const TeamPlayerList: React.FC<TeamPlayerListProps> = ({ players }) => {
  return (
    <>
      {players.map((player: Player) => (
        <TeamPlayer key={player.id} player={player} />
      ))}
    </>
  );
};

export default TeamPlayerList;