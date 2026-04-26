package com.cyld.booking.booking;

import org.springframework.stereotype.Service;

public interface BookingService {

    BookingResponse submit(BookingRequest request);
}

@Service
class PendingMailBookingService implements BookingService {

    @Override
    public BookingResponse submit(BookingRequest request) {
        return new BookingResponse(true, "Booking request sent.");
    }
}
