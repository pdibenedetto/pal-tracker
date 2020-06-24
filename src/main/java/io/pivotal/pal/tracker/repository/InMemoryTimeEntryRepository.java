package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private final LongSequenceGenerator sequenceGenerator;

    private final Map<Long, TimeEntry> repository;

    public InMemoryTimeEntryRepository() {
        repository = new ConcurrentHashMap<>();
        sequenceGenerator = new LongSequenceGeneratorImpl();
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(this.repository.values());
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(this.sequenceGenerator.getNext());
        this.repository.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return this.repository.get(timeEntryId);
    }

    @Override
    public void delete(long id) {
        this.repository.remove(id);
    }

    @Override
    public TimeEntry update(long key, TimeEntry timeEntry) {
        if(this.repository.get(key) == null) {
            return null;
        }

        timeEntry.setId(key);
        this.repository.put(key, timeEntry);
        return timeEntry;
    }
}
