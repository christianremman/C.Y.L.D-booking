package com.cyld.booking.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

class InMemoryBookingRateLimiterTests {

    @Test
    void blocksRequestsAfterConfiguredLimitWithinWindow() {
        MutableClock clock = new MutableClock(Instant.parse("2026-04-29T10:00:00Z"));
        RateLimitProperties properties = new RateLimitProperties();
        properties.setMaxRequests(2);
        properties.setWindow(Duration.ofMinutes(1));

        InMemoryBookingRateLimiter limiter = new InMemoryBookingRateLimiter(properties, clock);

        assertThat(limiter.allow("203.0.113.10")).isTrue();
        assertThat(limiter.allow("203.0.113.10")).isTrue();
        assertThat(limiter.allow("203.0.113.10")).isFalse();
    }

    @Test
    void cleanupStaleEntriesRemovesExpiredIps() {
        MutableClock clock = new MutableClock(Instant.parse("2026-04-29T10:00:00Z"));
        RateLimitProperties properties = new RateLimitProperties();
        properties.setMaxRequests(5);
        properties.setWindow(Duration.ofMinutes(1));

        InMemoryBookingRateLimiter limiter = new InMemoryBookingRateLimiter(properties, clock);

        limiter.allow("203.0.113.10");
        limiter.allow("203.0.113.20");
        assertThat(limiter.trackedIpCount()).isEqualTo(2);

        clock.advance(Duration.ofMinutes(2));
        limiter.cleanupStaleEntries();

        assertThat(limiter.trackedIpCount()).isEqualTo(0);
    }

    @Test
    void cleanupStaleEntriesRetainsActiveIps() {
        MutableClock clock = new MutableClock(Instant.parse("2026-04-29T10:00:00Z"));
        RateLimitProperties properties = new RateLimitProperties();
        properties.setMaxRequests(5);
        properties.setWindow(Duration.ofMinutes(1));

        InMemoryBookingRateLimiter limiter = new InMemoryBookingRateLimiter(properties, clock);

        limiter.allow("203.0.113.10");
        clock.advance(Duration.ofSeconds(30));
        limiter.allow("203.0.113.20");

        clock.advance(Duration.ofSeconds(31));
        limiter.cleanupStaleEntries();

        assertThat(limiter.trackedIpCount()).isEqualTo(1);
    }

    @Test
    void allowsRequestsAgainAfterWindowExpires() {
        MutableClock clock = new MutableClock(Instant.parse("2026-04-29T10:00:00Z"));
        RateLimitProperties properties = new RateLimitProperties();
        properties.setMaxRequests(1);
        properties.setWindow(Duration.ofSeconds(30));

        InMemoryBookingRateLimiter limiter = new InMemoryBookingRateLimiter(properties, clock);

        assertThat(limiter.allow("203.0.113.10")).isTrue();
        assertThat(limiter.allow("203.0.113.10")).isFalse();

        clock.advance(Duration.ofSeconds(31));

        assertThat(limiter.allow("203.0.113.10")).isTrue();
    }

    private static final class MutableClock extends Clock {
        private Instant currentInstant;

        private MutableClock(Instant currentInstant) {
            this.currentInstant = currentInstant;
        }

        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return currentInstant;
        }

        private void advance(Duration duration) {
            currentInstant = currentInstant.plus(duration);
        }
    }
}
