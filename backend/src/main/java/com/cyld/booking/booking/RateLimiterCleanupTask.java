package com.cyld.booking.booking;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class RateLimiterCleanupTask {

    private final BookingRateLimiter perIpLimiter;
    private final BookingRateLimiter globalLimiter;

    RateLimiterCleanupTask(
            @Qualifier("bookingRateLimiter") BookingRateLimiter perIpLimiter,
            @Qualifier("global") BookingRateLimiter globalLimiter
    ) {
        this.perIpLimiter = perIpLimiter;
        this.globalLimiter = globalLimiter;
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
