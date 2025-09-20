import React from 'react';
import { useParams } from 'react-router-dom';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import Loader from '@/ui/Loader';
import { Users } from 'lucide-react';
import { TableRow } from '../types/TableList';
import SimulationTeam from '../components/SimulationTeam';
import { useSimulation } from '../hooks/useSimulation';

const SimulationPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { tableData, loading, error } = useSimulation(id);

  if (loading) {
    return <Loader />;
  }
  
  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!tableData) {
    return <div className="text-center p-4">League data not found.</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <div className='flex items-center'>
                <h3>Simulation Results</h3>
            </div>
          </div>

          <div className="flex">
            <div className="flex items-center p-r-4 text-gray-200">
                <Users size={15} className="m-r-1" />
                <span className="text-sm">
                    {tableData.length} teams
                </span>
            </div>
          </div>
        </CardHeader>

        <CardContent className="m-y-4">
            {tableData.map((row: TableRow) => (
                <SimulationTeam key={row.id} row={row} />
            ))}
        </CardContent>
      </Card>
    </div>
  );
};

export default SimulationPage;