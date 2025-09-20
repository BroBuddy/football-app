package com.buddy.football;

import com.buddy.football.league.service.LeagueService;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.repository.NationRepository;
import com.buddy.football.nation.service.NationService;
import com.buddy.football.player.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ContextConfiguration(classes = FootballApplicationTests.TestConfig.class)
class FootballApplicationTests {

	@Test
	void contextLoads() {
	}

    @Configuration
    static class TestConfig {

        @MockitoBean
        private NationRepository nationRepository;

        @MockitoBean
        private NationMapper nationMapper;

        @MockitoBean
        private NationService nationService;

        @MockitoBean
        private PlayerService playerService;

        @MockitoBean
        private LeagueService leagueService;
    }

}
