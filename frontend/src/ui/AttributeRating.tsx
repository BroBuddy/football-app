import React from 'react';
import OverallRating from './OverallRating';
import { AttackingAttributes, DefendingAttributes, GoalkeepingAttributes, MentalityAttributes, MovementAttributes, PowerAttributes, SkillAttributes } from '@/player/types/PlayerAttributes';

interface AttributeRatingProps {
  title?: string;
  attributes: AttackingAttributes |
    SkillAttributes |
    MovementAttributes |
    PowerAttributes |
    MentalityAttributes |
    DefendingAttributes |
    GoalkeepingAttributes;
}

const AttributeRating: React.FC<AttributeRatingProps> = ({ title, attributes }) => {

  const formatKey = (key: string): string => {
    const spacedKey = key.replace(/([A-Z])/g, ' $1');
    return spacedKey.charAt(0).toUpperCase() + spacedKey.slice(1);
  };

  return (
    <div className="flex flex-col gap-1">
      {title && (
        <h4 className="text-lg text-white font-semibold mb-2">{title}</h4>
      )}

      {Object.entries(attributes).map(([key, value]) => (
        <div key={key} className="flex items-center">
          <OverallRating rating={value} />

          <span className="text-sm text-grey-200 m-l-3">
            {formatKey(key)}
          </span>

        </div>
      ))}
    </div>
  );
};

export default AttributeRating;