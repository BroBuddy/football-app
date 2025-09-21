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
                <div className='mb-5'>
                    {isGoalkeeper && (
                        <AttributeRating
                            title="Goalkeeping"
                            attributes={player.attributes.goalkeepingAttributes}
                        />
                    )}

                    {!isGoalkeeper && (
                        <div className='grid'>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Attacking" attributes={player.attributes.attackingAttributes} />
                            </div>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Skill" attributes={player.attributes.skillAttributes} />
                            </div>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Power" attributes={player.attributes.powerAttributes} />
                            </div>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Mentality" attributes={player.attributes.mentalityAttributes} />
                            </div>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Movement" attributes={player.attributes.movementAttributes} />
                            </div>
                            <div className='col-12 sm:col-6 md:col-4'>
                                <AttributeRating title="Defending" attributes={player.attributes.defendingAttributes} />
                            </div>
                        </div>
                    )}
                </div>
            </CardContent>
        </Card>
    );
};

export default PlayerAttributes;