package com.cyld.booking.booking;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotBlank(message = "Name is required.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

        @NotBlank(message = "Phone is required.")
        String phone,

        @NotNull(message = "Event date is required.")
        LocalDate eventDate,

        @NotBlank(message = "Event location is required.")
        String eventLocation,

        @NotBlank(message = "Event type is required.")
        String eventType,

        String budget,

        @NotBlank(message = "Message is required.")
        String message
) {
}
