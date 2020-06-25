package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository repository;
    private final DistributionSummary timeEntryDistributionSummary;
    private final Counter timeEntryActionCounter;

    public TimeEntryController(TimeEntryRepository repository,
                               MeterRegistry meterRegistry) {
        this.repository = repository;
        this.timeEntryDistributionSummary = meterRegistry.summary("timeEntry.summary");
        this.timeEntryActionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = this.repository.list();
        timeEntryActionCounter.increment();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = this.repository.find(id);
        if (timeEntry != null) {
            timeEntryActionCounter.increment();
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry persistedTimeEntry = repository.create(timeEntry);
        timeEntryActionCounter.increment();
        timeEntryDistributionSummary.record(this.repository.list().size());
        return new ResponseEntity<>(persistedTimeEntry, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        timeEntry = this.repository.update(id, timeEntry);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        this.repository.delete(id);
        timeEntryActionCounter.increment();
        timeEntryDistributionSummary.record(repository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
