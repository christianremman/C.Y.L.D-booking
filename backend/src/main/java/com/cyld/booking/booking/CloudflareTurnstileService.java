package com.cyld.booking.booking;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CloudflareTurnstileService implements TurnstileVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(CloudflareTurnstileService.class);

    private final RestTemplate restTemplate;
    private final TurnstileProperties properties;

    public CloudflareTurnstileService(RestTemplate restTemplate, TurnstileProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    public boolean verify(String token, String clientIp) {
        if (!StringUtils.hasText(properties.getSecret())) {
            logger.info("Turnstile verification skipped because TURNSTILE_SECRET is not configured.");
            return true;
        }

        if (!StringUtils.hasText(token)) {
            return false;
        }

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("secret", properties.getSecret());
        requestBody.add("response", token);

        if (StringUtils.hasText(clientIp)) {
            requestBody.add("remoteip", clientIp);
        }

        try {
            Map<?, ?> response = restTemplate.postForObject(
                    properties.getVerifyUrl(),
                    org.springframework.http.RequestEntity
                            .post(properties.getVerifyUrl())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .body(requestBody),
                    Map.class
            );

            return response != null && Boolean.TRUE.equals(response.get("success"));
        } catch (RestClientException exception) {
            logger.error("Turnstile verification request failed.", exception);
            return false;
        }
    }
}
