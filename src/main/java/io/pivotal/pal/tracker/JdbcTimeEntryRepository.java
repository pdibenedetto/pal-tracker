package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private static final String SQL_INSERT_STMT = "insert into time_entries(project_id, user_id, date, hours) values(?,?,?,?)";

    private static final String SQL_FIND_STMT = "select * from time_entries where id = ?";

    private static final String SQL_UPDATE_STMT = "update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?";

    private static final String SQL_DELETE_STMT = "delete from time_entries where id = ?";

    private static final String SQL_GET_ALL_STMT = "select * from time_entries";

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query(SQL_GET_ALL_STMT,
                (resultSet, rowNumber) ->
                        new TimeEntry(
                                resultSet.getLong("id"),
                                resultSet.getLong("project_id"),
                                resultSet.getLong("user_id"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getInt("hours")
                        ));
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_INSERT_STMT,
                            Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setDate(3, Date.valueOf(timeEntry.getDate()));
            ps.setInt(4, timeEntry.getHours());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey() == null ? -1L : keyHolder.getKey().longValue();
        timeEntry.setId(generatedId);

        return find(generatedId);
    }

    @Override
    public TimeEntry find(long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_STMT,
                    new Object[]{id}, (rs, rowNum) ->
                            new TimeEntry(
                                    rs.getLong("id"),
                                    rs.getLong("project_id"),
                                    rs.getLong("user_id"),
                                    rs.getDate("date").toLocalDate(),
                                    rs.getInt("hours")
                            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_STMT, id);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update(SQL_UPDATE_STMT,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                id);
        return find(id);
    }
}
