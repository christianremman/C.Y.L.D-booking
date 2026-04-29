package com.cyld.booking.booking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(WebConfig.class)
@WebMvcTest(BookingController.class)
@TestPropertySource(properties = "booking.cors.allowed-origin=https://booking.example.com")
class BookingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @MockitoBean
    private TurnstileVerificationService turnstileVerificationService;

    @MockitoBean
    private BookingRateLimiter bookingRateLimiter;

    @Test
    void acceptsValidRequestAndDelegatesToService() throws Exception {
        when(bookingRateLimiter.allow("127.0.0.1")).thenReturn(true);
        when(turnstileVerificationService.verify("token-123", "127.0.0.1")).thenReturn(true);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Booking request sent."));

        verify(bookingService).sendBookingRequest(argThat(request ->
                "Alex Booker".equals(request.name())
                        && "alex@example.com".equals(request.email())
                        && "Oslo".equals(request.eventLocation())
                        && "Club night".equals(request.eventType())));
    }

    @Test
    void rejectsInvalidRequiredFields() throws Exception {
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "email": "not-an-email",
                                  "phone": "",
                                  "eventDate": null,
                                  "eventLocation": "",
                                  "eventType": "",
                                  "message": "",
                                  "turnstileToken": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isNotEmpty());

        verifyNoInteractions(bookingService, bookingRateLimiter, turnstileVerificationService);
    }

    @Test
    void rejectsOversizedPayload() throws Exception {
        String largeMessage = "x".repeat(2001);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson().replace(
                                "\"We want to book C.Y.L.D for a late set.\"",
                                "\"" + largeMessage + "\"")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

        verifyNoInteractions(bookingService, bookingRateLimiter, turnstileVerificationService);
    }

    @Test
    void rejectsRequestsThatExceedRateLimit() throws Exception {
        when(bookingRateLimiter.allow("127.0.0.1")).thenReturn(false);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Booking request could not be sent. Please try again later."));

        verifyNoInteractions(bookingService, turnstileVerificationService);
    }

    @Test
    void rejectsInvalidTurnstileTokens() throws Exception {
        when(bookingRateLimiter.allow("127.0.0.1")).thenReturn(true);
        when(turnstileVerificationService.verify("token-123", "127.0.0.1")).thenReturn(false);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Booking request could not be sent. Please try again."));

        verifyNoInteractions(bookingService);
    }

    @Test
    void returnsSafeErrorWhenEmailDeliveryFails() throws Exception {
        when(bookingRateLimiter.allow("127.0.0.1")).thenReturn(true);
        when(turnstileVerificationService.verify("token-123", "127.0.0.1")).thenReturn(true);
        doThrow(new BookingEmailException("smtp detail")).when(bookingService).sendBookingRequest(any());

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Booking request could not be sent. Please try again later."));
    }

    @Test
    void allowsConfiguredFrontendOriginForBookingRequests() throws Exception {
        mockMvc.perform(options("/api/bookings")
                        .header("Origin", "https://booking.example.com")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://booking.example.com"));
    }

    private static String validRequestJson() {
        return """
                {
                  "name": "Alex Booker",
                  "email": "alex@example.com",
                  "phone": "+47 123 45 678",
                  "eventDate": "2026-05-20",
                  "eventLocation": "Oslo",
                  "eventType": "Club night",
                  "budget": "15000 NOK",
                  "message": "We want to book C.Y.L.D for a late set.",
                  "turnstileToken": "token-123"
                }
                """;
    }
}
