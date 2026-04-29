package com.cyld.booking.booking;

public interface TurnstileVerificationService {

    boolean verify(String token, String clientIp);
}
