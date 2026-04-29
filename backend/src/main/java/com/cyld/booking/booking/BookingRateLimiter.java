package com.cyld.booking.booking;

public interface BookingRateLimiter {

    boolean allow(String clientIp);
}
