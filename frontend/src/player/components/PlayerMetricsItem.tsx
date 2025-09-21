import React from 'react';

interface PlayerMetricsItemProps {
  name: string;
  value: number;
}

const PlayerMetricsItem: React.FC<PlayerMetricsItemProps> = ({ name, value }) => {
  return (
    <div className="flex justify-between items-center bg-grey-900 rounded p-2 m-b-1">
      <div className="text-sm text-white">
        {name}
      </div>

      <div className="text-sm text-grey-300">
        {value.toFixed(2)}
      </div>
    </div>
  );
};

export default PlayerMetricsItem;