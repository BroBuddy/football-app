package com.buddy.football.config;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.service.NationService;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeepAliveSchedulerTest {

    @Mock
    private NationService nationService;

    private KeepAliveScheduler keepAliveScheduler;

    private Logger logger;
    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void setUp() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
        loggerContext.start();

        logger = (Logger) LoggerFactory.getLogger(KeepAliveScheduler.class);
        logger.detachAndStopAllAppenders();

        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);

        keepAliveScheduler = new KeepAliveScheduler(nationService) {
            @Override
            protected void sleep(long millis) {
            }
        };
    }

    @AfterEach
    void tearDown() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

    @Test
    void shouldLogSuccessAndBreakLoopOnFirstAttempt() throws InterruptedException {
        NationDTO nationDTO = new NationDTO(UUID.randomUUID(), "Deutschland", "DEU", "deu.png");
        when(nationService.getNationByCode("DEU")).thenReturn(Optional.of(nationDTO));

        keepAliveScheduler.pingNations();

        verify(nationService, times(1)).getNationByCode("DEU");

        assertThat(logAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly("Ping: Deutschland");

        assertThat(logAppender.list)
                .extracting(ILoggingEvent::getLevel)
                .doesNotContain(Level.ERROR);
    }

    @Test
    void shouldRetryTwiceAndFailAfterThirdAttempt() throws InterruptedException {
        when(nationService.getNationByCode("DEU")).thenReturn(Optional.empty());

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