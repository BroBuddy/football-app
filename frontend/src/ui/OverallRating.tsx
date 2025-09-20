import React from 'react';

interface OverallRatingProps {
  rating: number;
}

const OverallRating: React.FC<OverallRatingProps> = ({ rating }) => {
  const getBackgroundColor = (value: number): string => {
    if (value >= 80) {
      return '#418a43';
    }
    if (value >= 70) {
      return '#71a927';
    }
    if (value >= 60) {
      return '#f6c935';
    }
    if (value >= 50) {
      return '#f28827';
    }
    return '#d44434';
  };

  const backgroundColor = getBackgroundColor(rating);

  return (
    <div
      style={{
        backgroundColor: backgroundColor,
      }}
      className="p-1 rounded flex items-center justify-center w-2"
    >
      <span className="text-black text-sm">
        {rating}
      </span>
    </div>
  );
};

export default OverallRating;