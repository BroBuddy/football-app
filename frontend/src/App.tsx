import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.scss'
import LeaguePage from './league/pages/LeaguePage'
import TeamDetail from './team/pages/TeamDetail'
import PlayerDetailPage from './player/pages/PlayerDetailPage'
import NavBar from './ui/NavBar'
import PlayerSearchPage from './player/pages/PlayerSearchPage'
import SimulationPage from './simulation/pages/SimulationPage'
import OverviewPage from './overview/pages/OverviewPage'
import ScoutingPage from './scouting/pages/ScoutingPage'

function App() {
  return (
    <>
      <BrowserRouter>
        <NavBar />

        <Routes>
          <Route path="/" element={<OverviewPage />} />
          <Route path="/leagues" element={<LeaguePage />} />
          <Route path="/teams/:id" element={<TeamDetail />} />
          <Route path="/players" element={<PlayerSearchPage />} />
          <Route path="/players/:id" element={<PlayerDetailPage />} />
          <Route path="/simulation/:id" element={<SimulationPage />} />
          <Route path="/scouting" element={<ScoutingPage />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
