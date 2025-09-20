package com.buddy.football.config;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.service.NationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class KeepAliveScheduler {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveScheduler.class);

    private final NationService nationService;

    public KeepAliveScheduler(NationService nationService) {
        this.nationService = nationService;
    }

    @Scheduled(fixedRate = 600_000)
    public void pingNations() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            try {
                NationDTO nation = nationService.getNationByCode("DEU")
                        .orElseThrow();

                log.info("Ping: {}", nation.name());

                break;
            } catch (Exception e) {
                if (i == 2) {
                    log.error("Alle Versuche fehlgeschlagen", e);
                } else {
                    Thread.sleep(2000);
                }
            }
        }
    }
}
