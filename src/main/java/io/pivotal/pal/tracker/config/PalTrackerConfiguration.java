package io.pivotal.pal.tracker.config;

import io.pivotal.pal.tracker.repository.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.repository.TimeEntryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PalTrackerConfiguration {
    @Bean
    public TimeEntryRepository getTimeEntryRepository() {
        return new InMemoryTimeEntryRepository();
    }
}
