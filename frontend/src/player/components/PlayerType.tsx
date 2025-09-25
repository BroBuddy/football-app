import { MainAttributes } from "../types/PlayerAttributes";
import Badge from "@/ui/Badge";

interface PlayerTypeProps {
  mainAttributes: MainAttributes;
}

const abbrevMap: Record<string, string> = {
  pace: 'PAC',
  shooting: 'SHO',
  passing: 'PAS',
  dribbling: 'DRI',
  defending: 'DEF',
  physical: 'PHY',
};

const wordings: Record<string, string> = {
  PAC: 'Speedster',
  SHO: 'Finisher',
  PAS: 'Playmaker',
  DRI: 'Dribbler',
  DEF: 'Defender',
  PHY: 'Powerhouse',
};

const PlayerType: React.FC<PlayerTypeProps> = ({ mainAttributes }) => {
    const sorted = Object.entries(mainAttributes)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 3);

  const topKeys = sorted.map(([key]) => abbrevMap[key.toLowerCase()]);

  return (
    <div className="flex gap-3">
        {topKeys.map(key => (
            <Badge key={key}>
                {wordings[key]}
            </Badge>
        ))}
    </div>
  );
}

export default PlayerType;
