package com.cyld.booking.booking;

import org.junit.jupiter.api.Test;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmailBookingServiceTests {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);

    @Test
    void sendsFormattedBookingEmail() {
        EmailBookingService service = new EmailBookingService(
                mailSender,
                "booking@example.com",
                "recipient@example.com"
        );

        service.sendBookingRequest(request());

        var messageCaptor = forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertThat(message.getTo()).containsExactly("recipient@example.com");
        assertThat(message.getFrom()).isEqualTo("booking@example.com");
        assertThat(message.getReplyTo()).isEqualTo("alex@example.com");
        assertThat(message.getSubject()).isEqualTo("New C.Y.L.D booking request from Alex Booker");
        assertThat(message.getText())
                .contains("Name: Alex Booker")
                .contains("Email: alex@example.com")
                .contains("Phone: +47 123 45 678")
                .contains("Event date: 2026-05-20")
                .contains("Event location: Oslo")
                .contains("Event type: Club night")
                .contains("Budget: 15000 NOK")
                .contains("We want to book C.Y.L.D for a late set.");
    }

    @Test
    void wrapsMailSenderFailures() {
        EmailBookingService service = new EmailBookingService(
                mailSender,
                "booking@example.com",
                "recipient@example.com"
        );
        doThrow(new MailSendException("smtp failure")).when(mailSender).send(any(SimpleMailMessage.class));

        assertThatThrownBy(() -> service.sendBookingRequest(request()))
                .isInstanceOf(BookingEmailException.class)
                .hasMessage("Booking email delivery failed.");
    }

    @Test
    void rejectsMissingMailConfiguration() {
        EmailBookingService service = new EmailBookingService(mailSender, "", "");

        assertThatThrownBy(() -> service.sendBookingRequest(request()))
                .isInstanceOf(BookingEmailException.class)
                .hasMessage("MAIL_FROM is required.");
    }

    private static BookingRequest request() {
        return new BookingRequest(
                "Alex Booker",
                "alex@example.com",
                "+47 123 45 678",
                LocalDate.of(2026, 5, 20),
                "Oslo",
                "Club night",
                "15000 NOK",
                "We want to book C.Y.L.D for a late set.",
                "token-123"
        );
    }
}
