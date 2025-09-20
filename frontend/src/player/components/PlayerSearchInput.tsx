import { Delete } from 'lucide-react';
import React, { useRef } from 'react';

interface PlayerSearchInputProps {
  query: string;
  setQuery: (query: string) => void;
}

const PlayerSearchInput: React.FC<PlayerSearchInputProps> = ({ query, setQuery }) => {
  const inputRef = useRef<HTMLInputElement>(null);
  
  const handleClearInput = () => {
    setQuery('');
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(event.target.value);
  };

  return (
    <div className="flex items-center">
      <input
        ref={inputRef}
        type="text"
        placeholder="Search player..."
        value={query}
        onChange={handleInputChange}
        className="w-full p-2 border rounded bg-black text-white"
      />

      {query && (
        <span
          onClick={handleClearInput}
          className="bg-black m-l-2 text-white cursor-pointer"
        >
          <Delete size={20} />
        </span>
      )}
    </div>
  );
};

export default PlayerSearchInput;