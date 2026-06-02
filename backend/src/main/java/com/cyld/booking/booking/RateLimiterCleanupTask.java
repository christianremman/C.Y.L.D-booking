package com.cyld.booking.booking;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class RateLimiterCleanupTask {

    private final InMemoryBookingRateLimiter perIpLimiter;
    private final InMemoryBookingRateLimiter globalLimiter;

    RateLimiterCleanupTask(
            @Qualifier("bookingRateLimiter") BookingRateLimiter perIpLimiter,
            @Qualifier("global") BookingRateLimiter globalLimiter
    ) {
        if (!(perIpLimiter instanceof InMemoryBookingRateLimiter)) {
            throw new IllegalStateException(
                    "perIpLimiter must be InMemoryBookingRateLimiter, got: " + perIpLimiter.getClass().getName());
        }
        if (!(globalLimiter instanceof InMemoryBookingRateLimiter)) {
            throw new IllegalStateException(
                    "globalLimiter must be InMemoryBookingRateLimiter, got: " + globalLimiter.getClass().getName());
        }
        this.perIpLimiter = (InMemoryBookingRateLimiter) perIpLimiter;
        this.globalLimiter = (InMemoryBookingRateLimiter) globalLimiter;
    }

    @Scheduled(fixedDelay = 60_000)
    void cleanupPerIp() {
        perIpLimiter.cleanupStaleEntries();
    }

    @Scheduled(fixedDelay = 3_600_000)
    void cleanupGlobal() {
        globalLimiter.cleanupStaleEntries();
    }
}
