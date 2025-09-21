import OverallRating from '@/ui/OverallRating';
import React from 'react';

interface PlayerMetricsItemProps {
  name: string;
  value: number;
  maxValue: number;
}

const PlayerMetricsItem: React.FC<PlayerMetricsItemProps> = ({ name, value, maxValue }) => {
  const rawRating = (value / maxValue) * 100;
  const rating: number = Math.floor(Math.min(rawRating, 100));

  return (
    <div className="flex items-center bg-grey-900 rounded p-2 m-b-1">
      <OverallRating rating={rating} />

        <span className="text-sm text-grey-200 m-l-3">
            {name}
        </span>
    </div>
  );
};

export default PlayerMetricsItem;