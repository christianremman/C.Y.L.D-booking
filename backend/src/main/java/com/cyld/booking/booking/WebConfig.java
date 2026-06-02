package com.cyld.booking.booking;

import java.time.Clock;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({CorsProperties.class, RateLimitProperties.class, GlobalRateLimitProperties.class, TurnstileProperties.class})
public class WebConfig {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    InMemoryBookingRateLimiter bookingRateLimiter(RateLimitProperties rateLimitProperties, Clock clock) {
        return new InMemoryBookingRateLimiter(rateLimitProperties, clock);
    }

    @Bean
    @Qualifier("global")
    InMemoryBookingRateLimiter globalBookingRateLimiter(GlobalRateLimitProperties globalRateLimitProperties, Clock clock) {
        return new InMemoryBookingRateLimiter(globalRateLimitProperties, clock);
    }

    @Bean
    WebMvcConfigurer corsConfigurer(CorsProperties corsProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (!StringUtils.hasText(corsProperties.getAllowedOrigin())) {
                    return;
                }

                registry.addMapping("/api/bookings")
                        .allowedOrigins(corsProperties.getAllowedOrigin())
                        .allowedMethods("POST", "OPTIONS")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }
}
