package com.buddy.football.config;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.service.NationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeepAliveSchedulerTest {

    @Mock
    private NationService nationService;

    @InjectMocks
    private KeepAliveScheduler keepAliveScheduler;

    private ListAppender<ILoggingEvent> logAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = (Logger) LoggerFactory.getLogger(KeepAliveScheduler.class);
        logger.detachAndStopAllAppenders();
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @Test
    void shouldLogSuccessAndBreakLoopOnFirstAttempt() throws InterruptedException {
        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Deutschland", "DEU", "deu.png");
        when(nationService.getNationByCode("DEU"))
                .thenReturn(Optional.of(nationDTO));

        keepAliveScheduler.pingNations();

        verify(nationService, times(1)).getNationByCode("DEU");

        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent loggingEvent = logAppender.list.get(0);

        assertThat(loggingEvent.getMessage()).isEqualTo("Ping: {}");
        assertThat(loggingEvent.getArgumentArray()).containsExactly("Deutschland");

        assertThat(logAppender.list)
                .extracting(ILoggingEvent::getLevel)
                .doesNotContain(Level.ERROR);
    }

    @Test
    void shouldRetryTwiceAndFailAfterThirdAttempt() throws InterruptedException {
        when(nationService.getNationByCode("DEU"))
                .thenReturn(Optional.empty());

        keepAliveScheduler.pingNations();

        verify(nationService, times(3)).getNationByCode("DEU");

        assertThat(logAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Alle Versuche fehlgeschlagen");

        assertThat(logAppender.list)
                .extracting(ILoggingEvent::getLevel)
                .contains(Level.ERROR);
    }
}