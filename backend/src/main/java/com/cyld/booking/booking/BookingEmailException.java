package com.cyld.booking.booking;

public class BookingEmailException extends RuntimeException {

    public BookingEmailException(String message) {
        super(message);
    }

    public BookingEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
