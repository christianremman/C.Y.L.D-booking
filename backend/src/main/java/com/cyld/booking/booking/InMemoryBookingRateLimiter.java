package com.cyld.booking.booking;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class InMemoryBookingRateLimiter implements BookingRateLimiter {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryBookingRateLimiter.class);
    private static final int MAX_IP_ENTRIES = 100_000;

    private final ConcurrentMap<String, Deque<Instant>> requestHistoryByIp = new ConcurrentHashMap<>();
    private final RateLimitConfig properties;
    private final Clock clock;

    public InMemoryBookingRateLimiter(RateLimitConfig properties, Clock clock) {
        this.properties = properties;
        this.clock = clock;
    }

    @Override
    public boolean allow(String clientIp) {
        String key = StringUtils.hasText(clientIp) ? clientIp : "unknown";

        Instant now = clock.instant();
        Instant cutoff = now.minus(properties.getWindow());
        boolean[] allowed = {false};

        // compute() is atomic per key in ConcurrentHashMap, eliminating the race between
        // allow() and cleanupStaleEntries() that existed with get() + putIfAbsent() + synchronized.
        requestHistoryByIp.compute(key, (k, deque) -> {
            if (deque == null) {
                // Size check is approximate: cap is a soft limit.
                // Overshoot bounded by number of concurrent threads hitting new keys simultaneously.
                if (requestHistoryByIp.size() >= MAX_IP_ENTRIES) {
                    logger.warn("Rate limit map at capacity, rejecting new key={}", k);
                    return null;
                }
                deque = new ArrayDeque<>();
            }
            while (!deque.isEmpty() && deque.peekFirst().isBefore(cutoff)) {
                deque.removeFirst();
            }
            if (deque.size() < properties.getMaxRequests()) {
                deque.addLast(now);
                allowed[0] = true;
            }
            return deque;
        });

        return allowed[0];
    }

    void cleanupStaleEntries() {
        Instant cutoff = clock.instant().minus(properties.getWindow());
        for (String key : requestHistoryByIp.keySet()) {
            requestHistoryByIp.computeIfPresent(key, (k, deque) -> {
                while (!deque.isEmpty() && deque.peekFirst().isBefore(cutoff)) {
                    deque.removeFirst();
                }
                return deque.isEmpty() ? null : deque;
            });
        }
    }

    int trackedIpCount() {
        return requestHistoryByIp.size();
    }
}
