package io.pivotal.pal.tracker.repository;

import io.pivotal.pal.tracker.model.TimeEntry;

import java.util.List;

public interface TimeEntryRepository {
    List<TimeEntry> list();

    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long timeEntryId);

    void delete(long id);

    TimeEntry update(long l, TimeEntry timeEntry);
}
