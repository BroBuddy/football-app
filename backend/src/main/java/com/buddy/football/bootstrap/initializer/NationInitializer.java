package com.buddy.football.bootstrap.initializer;

import com.buddy.football.bootstrap.data.NationData;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class NationInitializer implements CommandLineRunner {

    private final NationRepository nationRepository;

    public NationInitializer(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    @Override
    public void run(String... args) {
        if (nationRepository.count() == 0) {
            List<Nation> nations = NationData.get();
            nationRepository.saveAll(nations);
            System.out.println("✅ Nations initialized: " + nations.size());
        } else {
            System.out.println("ℹ️ Nations already exist, skipping initialization");
        }
    }
}
