import React from 'react';
import OverallRating from '@/ui/OverallRating';
import { formatKey } from '@/utils';

interface ScoreCardProps {
    name: string;
    rating: number;
}

const ScoreCard: React.FC<ScoreCardProps> = ({ name, rating }) => {
    return (
        <div className="flex items-center bg-grey-900 rounded p-2 m-b-1">
            <OverallRating rating={rating} />

            <span className="text-sm text-grey-200 m-l-3">
                {formatKey(name)}
            </span>
        </div>
    );
};

export default ScoreCard;