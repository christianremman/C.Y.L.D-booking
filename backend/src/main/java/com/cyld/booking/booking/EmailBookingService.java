package com.cyld.booking.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailBookingService implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(EmailBookingService.class);

    private final JavaMailSender mailSender;
    private final String mailFrom;
    private final String bookingRecipient;
    private final String bookingBcc;

    public EmailBookingService(
            JavaMailSender mailSender,
            @Value("${booking.mail.from:}") String mailFrom,
            @Value("${booking.mail.recipient:}") String bookingRecipient,
            @Value("${booking.mail.bcc:}") String bookingBcc
    ) {
        this.mailSender = mailSender;
        this.mailFrom = mailFrom;
        this.bookingRecipient = bookingRecipient;
        this.bookingBcc = bookingBcc;
    }

    @Override
    public void sendBookingRequest(BookingRequest request) {
        requireMailAddress(mailFrom, "MAIL_FROM is required.");
        requireMailAddress(bookingRecipient, "BOOKING_RECIPIENT is required.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(bookingRecipient);
        message.setFrom(mailFrom);
        message.setReplyTo(request.email());
        message.setSubject("New C.Y.L.D booking request from " + request.name());
        message.setText(formatBody(request));
        if (StringUtils.hasText(bookingBcc)) {
            message.setBcc(bookingBcc);
        }

        try {
            mailSender.send(message);
        } catch (MailException exception) {
            logger.error("Booking email delivery failed for recipient={}", bookingRecipient, exception);
            throw new BookingEmailException("Booking email delivery failed.", exception);
        }
    }

    private static void requireMailAddress(String value, String message) {
        if (!StringUtils.hasText(value)) {
            logger.warn("Booking mail configuration is missing: {}", message);
            throw new BookingEmailException(message);
        }
    }

    private static String formatBody(BookingRequest request) {
        StringBuilder body = new StringBuilder()
                .append("New booking request\n\n")
                .append("Name: ").append(request.name()).append('\n')
                .append("Email: ").append(request.email()).append('\n')
                .append("Phone: ").append(request.phone()).append('\n')
                .append("Event date: ").append(request.eventDate()).append('\n')
                .append("Event location: ").append(request.eventLocation()).append('\n')
                .append("Event type: ").append(request.eventType()).append('\n');

        if (StringUtils.hasText(request.budget())) {
            body.append("Budget: ").append(request.budget()).append('\n');
        }

        return body.append("\nMessage:\n").append(request.message()).append('\n').toString();
    }
}
