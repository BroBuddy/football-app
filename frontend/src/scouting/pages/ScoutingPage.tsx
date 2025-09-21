import React, { useState } from 'react';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import ScoutingResult from '../components/ScoutingResult';
import ScoutingForm from '../components/ScoutingForm';
import { useScoutingPlayers } from '../hooks/useScoutingPlayers';
import Loader from '@/ui/Loader';

const ScoutingPage = () => {
    const [marketValueMax, setMarketValueMax] = useState<number>(25);
    const [position, setPosition] = useState<string>('st');
    const [minValue, setMinValue] = useState<number>(80);
    const [searched, setSearched] = useState<boolean>(false);

    const { players, loading, error, scoutPlayers } = useScoutingPlayers();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setSearched(true);
        scoutPlayers(marketValueMax, position, minValue);
    };

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className='grid'>
            <div className='col-12'>
                <Card>
                    <CardHeader>
                        <h2>Player Scouting</h2>
                    </CardHeader>

                    <CardContent>
                        <ScoutingForm
                            marketValueMax={marketValueMax}
                            setMarketValueMax={setMarketValueMax}
                            position={position}
                            setPosition={setPosition}
                            minValue={minValue}
                            setMinValue={setMinValue}
                            handleSubmit={handleSubmit}
                            loading={loading}
                        />
                    </CardContent>
                </Card>
            </div>

            <div className='col-12'>
                {loading && <Loader />}

                {!loading && searched && <ScoutingResult players={players} />}
            </div>
        </div>
    );
};

export default ScoutingPage;