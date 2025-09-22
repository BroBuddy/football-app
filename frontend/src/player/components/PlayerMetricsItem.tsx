import ScoreCard from '@/ui/ScoreCard';
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
    <ScoreCard rating={rating} name={name} />
  );
};

export default PlayerMetricsItem;