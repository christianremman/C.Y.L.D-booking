package com.cyld.booking.booking;

import java.util.Comparator;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private static final String GLOBAL_RATE_LIMIT_KEY = "global";

    private final BookingService bookingService;
    private final TurnstileVerificationService turnstileVerificationService;
    private final BookingRateLimiter bookingRateLimiter;
    private final BookingRateLimiter globalRateLimiter;

    public BookingController(
            BookingService bookingService,
            TurnstileVerificationService turnstileVerificationService,
            BookingRateLimiter bookingRateLimiter,
            @Qualifier("global") BookingRateLimiter globalRateLimiter
    ) {
        this.bookingService = bookingService;
        this.turnstileVerificationService = turnstileVerificationService;
        this.bookingRateLimiter = bookingRateLimiter;
        this.globalRateLimiter = globalRateLimiter;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request,
            HttpServletRequest httpRequest
    ) {
        String clientIp = extractClientIp(httpRequest);
        logger.info("Received booking request from ip={}", clientIp);

        if (!globalRateLimiter.allow(GLOBAL_RATE_LIMIT_KEY)) {
            logger.warn("Rejected booking request due to global rate limit ip={}", clientIp);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new BookingResponse(false, "Booking request could not be sent. Please try again later."));
        }

        if (!bookingRateLimiter.allow(clientIp)) {
            logger.warn("Rejected booking request due to rate limit ip={}", clientIp);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new BookingResponse(false, "Booking request could not be sent. Please try again later."));
        }

        if (!turnstileVerificationService.verify(request.turnstileToken(), clientIp)) {
            logger.warn("Rejected booking request due to invalid turnstile token ip={}", clientIp);
            return ResponseEntity.badRequest()
                    .body(new BookingResponse(false, "Booking request could not be sent. Please try again."));
        }

        try {
            bookingService.sendBookingRequest(request);
            return ResponseEntity.ok(new BookingResponse(true, "Booking request sent."));
        } catch (BookingEmailException exception) {
            logger.error("Booking email delivery failed ip={}", clientIp, exception);
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

        logger.warn("Rejected booking request due to validation failure message={}", message);
        return ResponseEntity.badRequest().body(new BookingResponse(false, message));
    }

    private String extractClientIp(HttpServletRequest request) {
        String cfIp = request.getHeader("CF-Connecting-IP");
        if (cfIp != null && !cfIp.isBlank()) {
            return cfIp.trim();
        }
        logger.warn("CF-Connecting-IP header absent, falling back to remoteAddr — traffic may be bypassing Cloudflare");
        return request.getRemoteAddr();
    }
}
