package com.cyld.booking.booking;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

class RateLimiterCleanupTaskTests {

    @Test
    void cleanupPerIpDelegatesToPerIpLimiter() {
        BookingRateLimiter perIpLimiter = mock(BookingRateLimiter.class);
        BookingRateLimiter globalLimiter = mock(BookingRateLimiter.class);
        RateLimiterCleanupTask task = new RateLimiterCleanupTask(perIpLimiter, globalLimiter);

        task.cleanupPerIp();

        verify(perIpLimiter).cleanupStaleEntries();
        verifyNoInteractions(globalLimiter);
    }

    @Test
    void cleanupGlobalDelegatesToGlobalLimiter() {
        BookingRateLimiter perIpLimiter = mock(BookingRateLimiter.class);
        BookingRateLimiter globalLimiter = mock(BookingRateLimiter.class);
        RateLimiterCleanupTask task = new RateLimiterCleanupTask(perIpLimiter, globalLimiter);

        task.cleanupGlobal();

        verify(globalLimiter).cleanupStaleEntries();
        verifyNoInteractions(perIpLimiter);
    }
}
