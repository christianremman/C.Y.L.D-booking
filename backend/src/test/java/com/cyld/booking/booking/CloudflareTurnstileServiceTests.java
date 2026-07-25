package com.cyld.booking.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class CloudflareTurnstileServiceTests {

    @Test
    void acceptsSuccessfulTurnstileVerification() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        TurnstileProperties properties = new TurnstileProperties();
        properties.setSecret("turnstile-secret");
        properties.setVerifyUrl("https://challenges.cloudflare.com/turnstile/v0/siteverify");

        server.expect(requestTo(properties.getVerifyUrl()))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED))
                .andRespond(withSuccess("{\"success\":true}", MediaType.APPLICATION_JSON));

        CloudflareTurnstileService service = new CloudflareTurnstileService(restTemplate, properties);

        assertThat(service.verify("token-123", "203.0.113.10")).isTrue();

        server.verify();
    }

    @Test
    void rejectsFailedTurnstileVerification() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        TurnstileProperties properties = new TurnstileProperties();
        properties.setSecret("turnstile-secret");
        properties.setVerifyUrl("https://challenges.cloudflare.com/turnstile/v0/siteverify");

        server.expect(requestTo(properties.getVerifyUrl()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"success\":false}", MediaType.APPLICATION_JSON));

        CloudflareTurnstileService service = new CloudflareTurnstileService(restTemplate, properties);

        assertThat(service.verify("token-123", "203.0.113.10")).isFalse();

        server.verify();
    }

    @Test
    void skipsVerificationWhenSecretNotConfigured() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        TurnstileProperties properties = new TurnstileProperties();

        CloudflareTurnstileService service = new CloudflareTurnstileService(restTemplate, properties);

        assertThat(service.verify("", "203.0.113.10")).isTrue();

        server.verify();
    }
}
