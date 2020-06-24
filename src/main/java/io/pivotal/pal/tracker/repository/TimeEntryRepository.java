package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    List<TimeEntry> list();

    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long timeEntryId);

    void delete(long id);

    TimeEntry update(long l, TimeEntry timeEntry);
}
