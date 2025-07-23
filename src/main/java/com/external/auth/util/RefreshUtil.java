package com.external.auth.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RefreshUtil {

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}
