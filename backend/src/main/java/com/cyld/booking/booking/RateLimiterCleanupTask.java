package com.cyld.booking.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class RateLimiterCleanupTask {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterCleanupTask.class);

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
        if (perIpLimiter instanceof InMemoryBookingRateLimiter impl) {
            impl.cleanupStaleEntries();
        } else {
            log.warn("perIpLimiter is not InMemoryBookingRateLimiter — cleanup skipped");
        }
    }

    @Scheduled(fixedDelay = 3_600_000)
    void cleanupGlobal() {
        if (globalLimiter instanceof InMemoryBookingRateLimiter impl) {
            impl.cleanupStaleEntries();
        } else {
            log.warn("globalLimiter is not InMemoryBookingRateLimiter — cleanup skipped");
        }
    }
}
