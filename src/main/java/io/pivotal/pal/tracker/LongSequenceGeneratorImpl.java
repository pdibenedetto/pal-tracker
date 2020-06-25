package io.pivotal.pal.tracker;

import java.util.concurrent.atomic.AtomicLong;

public class LongSequenceGeneratorImpl implements LongSequenceGenerator {
    private final AtomicLong sequenceNumber = new AtomicLong(1L);

    public long getNext() {
        return sequenceNumber.getAndIncrement();
    }
}
