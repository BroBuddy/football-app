import React from 'react';
import { Player } from '@/player/types/Player';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import TeamPlayer from '@/team/components/TeamPlayer';
import { getPlayerCountText } from '@/utils';

interface ScoutingResultProps {
    players: Player[];
}

const ScoutingResult: React.FC<ScoutingResultProps> = ({ players }) => {
    if (players.length > 0) {
        return (
            <Card>
                <CardHeader>
                    <h2>{getPlayerCountText(players.length)}</h2>
                </CardHeader>

                <CardContent className='m-b-3'>
                    {players.map((player: Player) => (
                        <TeamPlayer key={player.id} player={player} />
                    ))}
                </CardContent>
            </Card>
        );
    }
    
    return (
        <Card>
            <CardHeader>
                <h2>Scouting Results</h2>
            </CardHeader>

            <CardContent>
                <p>No players found. Try a different search.</p>
            </CardContent>
        </Card>
    );
};

export default ScoutingResult;