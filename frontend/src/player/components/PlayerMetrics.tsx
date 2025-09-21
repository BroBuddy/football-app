import { Card, CardContent, CardHeader } from '@/ui/Card';
import { Metrics } from '../types/PlayerMetrics';
import PlayerMetricsItem from './PlayerMetricsItem';

interface PlayerMetricsProps {
  metrics: Metrics;
}

export const METRIC_MAX_VALUES = {
  xG: 1.7,
  xA: 1.4,
  xT: 1.0,
  successfulDribbles: 6.3,
  progressivePasses: 8.9,
  tackleSuccess: 1.1,
  interceptions: 0.8,
  clearances: 0.9,
  pressureRecovery: 0.9,
};

const formatMetricName = (name: string): string => {
  const parts = name.split(/(?=[A-Z])/);
  return parts.map(part => part.charAt(0).toUpperCase() + part.slice(1)).join(' ');
};

const PlayerMetrics = ({ metrics }: PlayerMetricsProps) => {
  const offensiveMetrics = {
    xG: metrics.xG,
    xA: metrics.xA,
    xT: metrics.xT,
    successfulDribbles: metrics.successfulDribbles,
    progressivePasses: metrics.progressivePasses,
  };

  const defensiveMetrics = {
    tackleSuccess: metrics.tackleSuccess,
    interceptions: metrics.interceptions,
    clearances: metrics.clearances,
    pressureRecovery: metrics.pressureRecovery,
  };

  const renderMetrics = (metricData: { [key: string]: number }) => (
    <div>
      {Object.entries(metricData).map(([key, value]) => (
        <PlayerMetricsItem
          key={key}
          name={formatMetricName(key)}
          value={value}
          maxValue={METRIC_MAX_VALUES[key as keyof typeof METRIC_MAX_VALUES]}
        />
      ))}
    </div>
  );

  return (
    <div className="grid md:flex">
      <div className="col-12 md:col-6">
        <Card className='h-27'>
          <CardHeader>
            <h3>Offensive Metrics</h3>
          </CardHeader>

          <CardContent>
            {renderMetrics(offensiveMetrics)}
          </CardContent>
        </Card>
      </div>

      <div className="col-12 md:col-6">
        <Card className='h-27'>
          <CardHeader>
            <h3>Defensive Metrics</h3>
          </CardHeader>

          <CardContent>
            {renderMetrics(defensiveMetrics)}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default PlayerMetrics;