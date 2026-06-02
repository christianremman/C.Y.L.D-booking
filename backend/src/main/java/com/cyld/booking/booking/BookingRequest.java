package com.cyld.booking.booking;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookingRequest(
        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must be 100 characters or fewer.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        @Size(max = 254, message = "Email must be 254 characters or fewer.")
        String email,

        @NotBlank(message = "Phone is required.")
        @Size(max = 30, message = "Phone must be 30 characters or fewer.")
        String phone,

        @NotNull(message = "Event date is required.")
        @FutureOrPresent(message = "Event date must be today or in the future.")
        LocalDate eventDate,

        @NotBlank(message = "Event location is required.")
        @Size(max = 120, message = "Event location must be 120 characters or fewer.")
        String eventLocation,

        @NotBlank(message = "Event type is required.")
        @Size(max = 60, message = "Event type must be 60 characters or fewer.")
        String eventType,

        @Size(max = 60, message = "Budget must be 60 characters or fewer.")
        String budget,

        @NotBlank(message = "Message is required.")
        @Size(max = 2000, message = "Message must be 2000 characters or fewer.")
        String message,

        @NotBlank(message = "Turnstile token is required.")
        @Size(max = 2048, message = "Turnstile token must be 2048 characters or fewer.")
        String turnstileToken
) {
}
