import { Card, CardContent, CardHeader } from "@/ui/Card";
import { useBestPlayers } from "../hooks/useBestPlayers";
import { PlayerDetail } from "../services/types/BestPlayers";
import TeamPlayer from "@/team/components/TeamPlayer";
import Loader from "@/ui/Loader";

const OverviewPage = () => {
  const { data, loading, error } = useBestPlayers();

  if (loading) {
    return <Loader />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!data) {
    return <div>No data available.</div>;
  }

  const formatAttributeName = (name: string): string => {
    return name.charAt(0).toUpperCase() + name.slice(1);
  };

  return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {Object.entries(data).map(([attribute, players]) => (
          <Card key={attribute}>
            <CardHeader>
              <h2>Best {formatAttributeName(attribute)} Players</h2>
            </CardHeader>
            
            <CardContent>
              {players.map((player: PlayerDetail) => (
                <TeamPlayer key={player.id} player={player} />
              ))}
            </CardContent>
          </Card>
        ))}
      </div>
  );
};

export default OverviewPage;