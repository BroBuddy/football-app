import React from 'react';
import { Radar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
  ChartOptions,
} from 'chart.js';

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

interface RadarChartProps {
  labels: string[];
  data: number[];
  chartTitle: string;
}

const COLOR_ZONES = {
  zone1: 'rgba(212, 68, 52, 0.2)',
  zone2: 'rgba(242, 136, 39, 0.2)',
  zone3: 'rgba(246, 201, 53, 0.2)',
  zone4: 'rgba(113, 169, 39, 0.2)',
  zone5: 'rgba(65, 138, 67, 0.2)',
};

const RadarChart: React.FC<RadarChartProps> = ({ labels, data, chartTitle }) => {
  const chartData = {
    labels: labels,
    datasets: [
      {
        label: '',
        data: labels.map(() => 19),
        backgroundColor: COLOR_ZONES.zone1,
        borderColor: COLOR_ZONES.zone1.replace('0.2', '1'),
        pointRadius: 0,
      },
      {
        label: '',
        data: labels.map(() => 39),
        backgroundColor: COLOR_ZONES.zone2,
        borderColor: COLOR_ZONES.zone2.replace('0.2', '1'),
        pointRadius: 0,
      },
      {
        label: '',
        data: labels.map(() => 59),
        backgroundColor: COLOR_ZONES.zone3,
        borderColor: COLOR_ZONES.zone3.replace('0.2', '1'),
        pointRadius: 0,
      },
      {
        label: '',
        data: labels.map(() => 79),
        backgroundColor: COLOR_ZONES.zone4,
        borderColor: COLOR_ZONES.zone4.replace('0.2', '1'),
        pointRadius: 0,
      },
      {
        label: '',
        data: labels.map(() => 99),
        backgroundColor: COLOR_ZONES.zone5,
        borderColor: COLOR_ZONES.zone5.replace('0.2', '1'),
        pointRadius: 0,
      },
      {
        label: chartTitle,
        data: data,
        backgroundColor: 'rgba(255, 255, 255, 0.3)',
        borderColor: 'white',
        borderWidth: 2,
        fill: true,
        pointBackgroundColor: 'white',
        pointBorderColor: 'white',
        pointHoverRadius: 5,
        pointRadius: 3,
        pointHitRadius: 10,
      },
    ].reverse(), 
  };

  const options: ChartOptions<'radar'> = {
    scales: {
      r: {
        angleLines: {
          display: false,
        },
        suggestedMin: 0,
        suggestedMax: 100,
        ticks: {
          display: false,
        },
        grid: {
          circular: true,
          color: 'rgba(255, 255, 255, 0.2)',
        },
        pointLabels: {
          font: {
            size: 10,
            family: 'Arial',
            weight: 'bold'
          },
          color: 'rgba(255, 255, 255, 0.8)',
        },
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        backgroundColor: 'rgba(0, 0, 0, 0.7)',
        bodyFont: {
          size: 12,
        },
      },
    },
  };

  return <Radar data={chartData} options={options} />;
};

export default RadarChart;