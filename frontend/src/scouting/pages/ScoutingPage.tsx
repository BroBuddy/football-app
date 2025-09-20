import React, { useState } from 'react';
import { Card, CardContent, CardHeader } from '@/ui/Card';
import ScoutingResult from '../components/ScoutingResult';
import ScoutingForm from '../components/ScoutingForm';
import { useScoutingPlayers } from '../hooks/useScoutingPlayers';

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
        <>
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

            {searched && <ScoutingResult players={players} />}
        </>
    );
};

export default ScoutingPage;