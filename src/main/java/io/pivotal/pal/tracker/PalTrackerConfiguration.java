package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class PalTrackerConfiguration {

    @Bean
    public DataSource getMySqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));
        dataSource.setUsername("tracker");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public TimeEntryRepository getTimeEntryRepository() {
        return new JdbcTimeEntryRepository(getMySqlDataSource());
    }
}
