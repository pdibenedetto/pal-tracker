package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PalTrackerConfiguration {

    //@Bean
    //public IN

    @Bean
    public TimeEntryRepository getTimeEntryRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTimeEntryRepository(jdbcTemplate);
    }
}
