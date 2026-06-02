package com.cyld.booking.booking;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "booking.global-rate-limit")
public class GlobalRateLimitProperties implements RateLimitConfig {

    private int maxRequests = 20;
    private Duration window = Duration.ofHours(1);

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public Duration getWindow() {
        return window;
    }

    public void setWindow(Duration window) {
        this.window = window;
    }
}
