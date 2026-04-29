package com.cyld.booking.booking;

import java.util.Comparator;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            bookingService.sendBookingRequest(request);
            return ResponseEntity.ok(new BookingResponse(true, "Booking request sent."));
        } catch (BookingEmailException exception) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new BookingResponse(false, "Booking request could not be sent. Please try again later."));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BookingResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .min(Comparator.comparing(FieldError::getField))
                .map(FieldError::getDefaultMessage)
                .orElse("Booking request is invalid.");

        return ResponseEntity.badRequest().body(new BookingResponse(false, message));
    }
}
