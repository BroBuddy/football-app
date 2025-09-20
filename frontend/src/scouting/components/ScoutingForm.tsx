import { Search } from 'lucide-react';
import React from 'react';

interface ScoutingFormProps {
    marketValueMax: number;
    setMarketValueMax: (value: number) => void;
    position: string;
    setPosition: (value: string) => void;
    minValue: number;
    setMinValue: (value: number) => void;
    handleSubmit: (e: React.FormEvent) => void;
    loading: boolean;
}

const positions: string[] = 
    ['st', 'lw', 'rw', 'cam', 'cm', 'cdm', 'rm', 'lm', 'cb', 'lb', 'rb', 'gk'];

const minValues: number[] = Array.from({ length: (86 - 70) / 2 + 1 }, (_, i) => 86 - (i * 2));

const maxMarketValues: number[] = [
    ...Array.from({ length: 5 }, (_, i) => 180 - (i * 20)),
    ...Array.from({ length: 5 }, (_, i) => 90 - (i * 10)),
    ...Array.from({ length: 6 }, (_, i) => 45 - (i * 5)),
    ...Array.from({ length: 9 }, (_, i) => 18 - (i * 2))
];

const ScoutingForm: React.FC<ScoutingFormProps> = ({ 
    marketValueMax, 
    setMarketValueMax, 
    position, 
    setPosition, 
    minValue, 
    setMinValue, 
    handleSubmit, 
    loading 
}) => {
    return (
        <form onSubmit={handleSubmit}>
            <div className='flex items-center justify-between'>
                <div className='w-20'>
                    <label htmlFor="position" className='text-sm text-grey-300 m-r-2'>
                        Position
                    </label>
                    <select
                        id="position"
                        value={position}
                        className='text-white bg-black p-2 rounded'
                        onChange={(e) => setPosition(e.target.value)}
                    >
                        <option value="" disabled>Select Position</option>
                        {positions.map((pos: string) => (
                            <option key={pos} value={pos}>
                                {pos.toUpperCase()}
                            </option>
                        ))}
                    </select>
                </div>

                <div className='w-20'>
                    <label htmlFor="minValue" className='text-sm text-grey-300 m-r-2'>
                        Min Value
                    </label>
                    <select
                        id="minValue"
                        value={minValue}
                        className='text-white bg-black p-2 rounded'
                        onChange={(e) => setMinValue(Number(e.target.value))}
                    >
                        <option value="" disabled>Select Value</option>
                        {minValues.map((value: number) => (
                            <option key={value} value={value}>
                                {value}
                            </option>
                        ))}
                    </select>
                </div>

                <div className='w-25'>
                    <label htmlFor="marketValueMax" className='text-sm text-grey-300 m-r-2'>
                        Max Market Value
                    </label>
                    <select
                        id="marketValueMax"
                        value={marketValueMax}
                        className='text-white bg-black p-2 rounded'
                        onChange={(e) => setMarketValueMax(Number(e.target.value))}
                    >
                        <option value={0} disabled>Select Value</option>
                        {maxMarketValues.map((value: number) => (
                            <option key={value} value={value}>
                                {value}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className='m-y-4'>
                <button type="submit"
                    className='flex p-2 bg-black text-white rounded cursor'
                    disabled={loading}>
                    <Search size={15} />
                    <span className='m-l-2'>Start Scouting</span>
                </button>
            </div>
        </form>
    );
};

export default ScoutingForm;