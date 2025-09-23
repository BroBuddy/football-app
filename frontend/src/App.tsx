import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LeagueListPage from './league/pages/LeagueListPage'
import LeagueDetailPage from './league/pages/LeagueDetailPage'
import TeamDetailPage from './team/pages/TeamDetailPage'
import PlayerDetailPage from './player/pages/PlayerDetailPage'
import PlayerListPage from './player/pages/PlayerListPage'
import SimulationPage from './simulation/pages/SimulationPage'
import OverviewPage from './overview/pages/OverviewPage'
import ScoutingPage from './scouting/pages/ScoutingPage'
import NavBar from './ui/NavBar'
import './App.scss'

function App() {
  return (
    <>
      <BrowserRouter>
        <NavBar />

        <Routes>
          <Route path="/" element={<OverviewPage />} />
          <Route path="/leagues" element={<LeagueListPage />} />
          <Route path="/leagues/:id" element={<LeagueDetailPage />} />
          <Route path="/leagues/:id/simulate" element={<SimulationPage />} />
          <Route path="/teams/:id" element={<TeamDetailPage />} />
          <Route path="/players" element={<PlayerListPage />} />
          <Route path="/players/:id" element={<PlayerDetailPage />} />
          <Route path="/scouting" element={<ScoutingPage />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
