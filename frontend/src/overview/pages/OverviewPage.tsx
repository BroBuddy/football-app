import { Card, CardContent, CardHeader } from "@/ui/Card";
import { useBestPlayers } from "../hooks/useBestPlayers";
import { PlayerDetail } from "../services/types/BestPlayers";
import Loader from "@/ui/Loader";
import OverviewPlayer from "../components/OverviewPlayer";

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
      <div className="grid">
        {Object.entries(data).map(([attribute, players]) => (
          <div key={attribute} className="col-12 md:col-6">
            <Card>
              <CardHeader>
                <h2>Best {formatAttributeName(attribute)} Players</h2>
              </CardHeader>

              <CardContent>
                {players.map((player: PlayerDetail) => (
                  <OverviewPlayer key={player.id} player={player} />
                ))}
              </CardContent>
            </Card>
          </div>
        ))}
      </div>
  );
};

export default OverviewPage;