package com.cyld.booking.booking;

import java.time.Duration;

public interface RateLimitConfig {
    int getMaxRequests();
    Duration getWindow();
}
