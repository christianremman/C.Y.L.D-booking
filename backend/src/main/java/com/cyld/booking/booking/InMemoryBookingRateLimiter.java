package com.cyld.booking.booking;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.StringUtils;

public class InMemoryBookingRateLimiter implements BookingRateLimiter {

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
        Deque<Instant> requestHistory = requestHistoryByIp.computeIfAbsent(key, ignored -> new ArrayDeque<>());
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
            return true;
        }
    }
}
