import { Player } from '../types/Player';
import RadarChart from '@/ui/RadarChart';
import React from 'react';
import { MainAttributes } from '../types/PlayerAttributes';

interface PlayerRadarProps {
  player: Player;
}

const attributeShorthands: { [key: string]: string } = {
  pace: 'PAC',
  shooting: 'SHO',
  passing: 'PAS',
  dribbling: 'DRI',
  defending: 'DEF',
  physical: 'PHY',
  gkdiving: 'DIV',
  gkhandling: 'HAN',
  gkkicking: 'KIC',
  gkreflexes: 'REF',
  gkpositioning: 'POS',
};

const PlayerRadar: React.FC<PlayerRadarProps> = ({ player }) => {
  const isGoalkeeper = player.mainPositions === 'gk';

  let labels: string[];
  let data: number[];
  let chartTitle: string;

  if (isGoalkeeper) {
    labels = Object.keys(player.attributes.goalkeepingAttributes) as string[];
    data = Object.values(player.attributes.goalkeepingAttributes) as number[];
    chartTitle = 'Goalkeeping Attributes';
  } else {
    const mainAttributesKeys = Object.keys(
      player.mainAttributes as MainAttributes
    ).filter((key) => key !== 'goalkeeping');

    labels = mainAttributesKeys as string[];
    data = Object.values(player.mainAttributes as MainAttributes)
      .filter(
        (_, index) =>
          Object.keys(player.mainAttributes as MainAttributes)[index] !==
          'goalkeeping'
      ) as number[];
    chartTitle = 'Player Attributes';
  }

  const formattedLabels = labels.map((label, index) => {
    const shorthand = attributeShorthands[label.toLowerCase()];
    const value = data[index];
    return `${shorthand || label.toUpperCase()}: ${value}`;
  });

  return (
    <div style={{ width: '300px', height: '300px' }}>
      <RadarChart
        labels={formattedLabels}
        data={data}
        chartTitle={chartTitle}
      />
    </div>
  );
};

export default PlayerRadar;