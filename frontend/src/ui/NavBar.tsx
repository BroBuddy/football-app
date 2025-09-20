import { Link } from 'react-router-dom';
import { Home, Search, Trophy, Users } from 'lucide-react';

const NavBar = () => {
  return (
    <nav className="fixed p-4 flex items-center gap-8">
      <Link to="/" className="flex items-center gap-2 text-white">
        <Home size={15} />
        <span className="text-md">Home</span>
      </Link>

      <Link to="/leagues" className="flex items-center gap-2 text-white">
        <Trophy size={15} />
        <span className="text-md">Leagues</span>
      </Link>

      <Link to="/players" className="flex items-center gap-2 text-white">
        <Users size={15} />
        <span className="text-md">Players</span>
      </Link>

      <Link to="/scouting" className="flex items-center gap-2 text-white">
        <Search size={15} />
        <span className="text-md">Scouting</span>
      </Link>
    </nav>
  );
};

export default NavBar;
