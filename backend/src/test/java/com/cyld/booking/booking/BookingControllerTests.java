package com.cyld.booking.booking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookingController.class)
class BookingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void acceptsValidRequestAndDelegatesToService() throws Exception {
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
                                  "message": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void returnsSafeErrorWhenEmailDeliveryFails() throws Exception {
        doThrow(new BookingEmailException("smtp detail")).when(bookingService).sendBookingRequest(any());

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Booking request could not be sent. Please try again later."));
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
                  "message": "We want to book C.Y.L.D for a late set."
                }
                """;
    }
}
