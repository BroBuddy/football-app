import React from 'react';
import { Player } from '../types/Player';
import PlayerSearchItem from './PlayerSearchItem';

interface PlayerSearchResultProps {
  players: Player[];
}

const PlayerSearchResult: React.FC<PlayerSearchResultProps> = ({ players }) => {
  return (
    <>
        {players.length > 0 && (
          players.map((player: Player) => (
            <PlayerSearchItem key={player.id} player={player} />
          ))
        )}
    </>
  );
};

export default PlayerSearchResult;