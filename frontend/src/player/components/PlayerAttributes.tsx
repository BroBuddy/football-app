import { Card, CardContent } from '@/ui/Card';
import React from 'react';
import { Player } from '../types/Player';
import { getStrengthsAndWeaknesses } from '../utils/playerHelpers';
import ScoreCard from '@/ui/ScoreCard';

interface PlayerAttributesProps {
    player: Player;
}

const PlayerAttributes: React.FC<PlayerAttributesProps> = ({ player }) => {
    const isGoalkeeper = player.mainPositions === 'gk';
    const { strengths, weaknesses } = getStrengthsAndWeaknesses(player.attributes, isGoalkeeper);

    return (
        <Card>
            <CardContent className='m-b-2'>
                <div className='grid'>
                    <div className='col-12 md:col-6'>
                        <h3>Strengths</h3>

                        {strengths.map(([name, rating]) => (
                            <ScoreCard key={name} rating={rating} name={name} />
                        ))}
                    </div>

                    <div className='col-12 md:col-6'>
                        <h3>Weaknesses</h3>

                        {weaknesses.map(([name, rating]) => (
                            <ScoreCard key={name} rating={rating} name={name} />
                        ))}
                    </div>
                </div>
            </CardContent>
        </Card>
    );
};

export default PlayerAttributes;