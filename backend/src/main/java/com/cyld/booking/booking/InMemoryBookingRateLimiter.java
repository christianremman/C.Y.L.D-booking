package com.cyld.booking.booking;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.StringUtils;

public class InMemoryBookingRateLimiter implements BookingRateLimiter {

    private static final int MAX_IP_ENTRIES = 100_000;

    private final ConcurrentMap<String, Deque<Instant>> requestHistoryByIp = new ConcurrentHashMap<>();
    private final RateLimitProperties properties;
    private final Clock clock;

    public InMemoryBookingRateLimiter(RateLimitProperties properties, Clock clock) {
        this.properties = properties;
        this.clock = clock;
    }

    @Override
    public boolean allow(String clientIp) {
        String key = StringUtils.hasText(clientIp) ? clientIp : "unknown";

        Deque<Instant> requestHistory = requestHistoryByIp.get(key);
        if (requestHistory == null) {
            if (requestHistoryByIp.size() >= MAX_IP_ENTRIES) {
                return false;
            }
            Deque<Instant> newDeque = new ArrayDeque<>();
            requestHistory = requestHistoryByIp.putIfAbsent(key, newDeque);
            if (requestHistory == null) {
                requestHistory = newDeque;
            }
        }

        Instant now = clock.instant();
        Instant cutoff = now.minus(properties.getWindow());

        synchronized (requestHistory) {
            while (!requestHistory.isEmpty() && requestHistory.peekFirst().isBefore(cutoff)) {
                requestHistory.removeFirst();
            }

            if (requestHistory.size() >= properties.getMaxRequests()) {
                return false;
            }

            requestHistory.addLast(now);
        }

        if (requestHistory.isEmpty()) {
            requestHistoryByIp.remove(key, requestHistory);
        }

        return true;
    }
}
