package com.buddy.football.bootstrap.initializer;

import com.buddy.football.team.entity.Team;
import com.buddy.football.team.repository.TeamRepository;
import com.buddy.football.bootstrap.data.TeamData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class TeamInitializerTest {

    private TeamRepository teamRepository;
    private TeamData teamData;
    private TeamInitializer teamInitializer;

    @BeforeEach
    void setup() {
        teamRepository = Mockito.mock(TeamRepository.class);
        teamData = Mockito.mock(TeamData.class);
        teamInitializer = new TeamInitializer(teamRepository, teamData);
    }

    @Test
    void testRun_initializesTeamsWhenEmpty() throws Exception {
        when(teamRepository.count()).thenReturn(0L);

        List<Team> dummyTeams = List.of(new Team("Dummy Team"));
        when(teamData.get()).thenReturn(dummyTeams);

        teamInitializer.run();

        verify(teamRepository, times(1)).saveAll(dummyTeams);
    }

    @Test
    void testRun_skipsInitializationWhenNotEmpty() throws Exception {
        when(teamRepository.count()).thenReturn(5L);

        teamInitializer.run();

        verify(teamRepository, never()).saveAll(any());
    }
}
