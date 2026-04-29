package com.cyld.booking.booking;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "turnstile")
public class TurnstileProperties {

    private String secret = "";
    private String verifyUrl = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getVerifyUrl() {
        return verifyUrl;
    }

    public void setVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
    }
}
