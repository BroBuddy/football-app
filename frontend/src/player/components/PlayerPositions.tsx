import React from 'react';
import OverallRating from '../../ui/OverallRating';
import fieldImage from '../../assets/field.png';
import './PlayerPositions.scss';

interface PlayerPositionsProps {
  positions: { [key: string]: number };
}

const positionCoordinates: { [key: string]: { top: string; left: string } } = {
  ST: { top: '10%', left: '50%' },
  LW: { top: '15%', left: '15%' },
  RW: { top: '15%', left: '85%' },

  CAM: { top: '28%', left: '50%' },
  LM: { top: '43%', left: '15%' },
  CM: { top: '43%', left: '50%' },
  RM: { top: '43%', left: '85%' },
  CDM: { top: '58%', left: '50%' },

  LB: { top: '75%', left: '15%' },
  RB: { top: '75%', left: '85%' },
  CB: { top: '75%', left: '50%' },

  GK: { top: '90%', left: '50%' },
};

const PlayerPositions: React.FC<PlayerPositionsProps> = ({ positions }) => {
  return (
    <div
      className="fieldContainer"
      style={{ backgroundImage: `url(${fieldImage})` }}
    >
      {Object.entries(positions).map(([positionKey, rating]) => {
        const upperCaseKey = positionKey.toUpperCase();
        const coords = positionCoordinates[upperCaseKey];

       if (coords) {
          return (
            <div
              key={positionKey}
              className="positionWrapper"
              style={{ top: coords.top, left: coords.left }}
            >
              <span className="positionLabel">
                {upperCaseKey}
              </span>
              <OverallRating rating={rating} />
            </div>
          );
        }

        return null;
      })}
    </div>
  );
};

export default PlayerPositions;