import { Card, CardContent } from '@/ui/Card';
import AttributeRating from '@/ui/AttributeRating';
import React from 'react';
import { Player } from '../types/Player';

interface PlayerAttributesProps {
    player: Player;
}

const PlayerAttributes: React.FC<PlayerAttributesProps> = ({ player }) => {
    const isGoalkeeper = player.mainPositions === 'gk';

    return (
        <Card>
            <CardContent className='m-b-4'>
                <div className='flex flex-row justify-between mb-5'>
                    {isGoalkeeper && (
                        <AttributeRating
                            title="Goalkeeping"
                            attributes={player.attributes.goalkeepingAttributes}
                        />
                    )}

                    {!isGoalkeeper && (
                        <>
                            <div>
                                <AttributeRating title="Attacking" attributes={player.attributes.attackingAttributes} />
                                <AttributeRating title="Skill" attributes={player.attributes.skillAttributes} />
                            </div>

                            <div>
                                <AttributeRating title="Movement" attributes={player.attributes.movementAttributes} />
                                <AttributeRating title="Power" attributes={player.attributes.powerAttributes} />
                            </div>

                            <div>
                                <AttributeRating title="Mentality" attributes={player.attributes.mentalityAttributes} />
                                <AttributeRating title="Defending" attributes={player.attributes.defendingAttributes} />
                            </div>
                        </>
                    )}
                </div>
            </CardContent>
        </Card>
    );
};

export default PlayerAttributes;