package com.dentalflow.auth.service.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record RsaKeyProperties(
        String privateKeyPath,
        String publicKeyPath
) {
}
