package com.dentalflow.auth.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;

@Component
@RequiredArgsConstructor
public class RsaKeyLoader {

    private final RsaKeyProperties properties;
    private final ResourceLoader resourceLoader;

    public PrivateKey loadPrivateKey() throws Exception {
        try (var inputStream = resourceLoader.getResource(properties.privateKeyPath()).getInputStream()) {
            String key = new String(inputStream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);

            key = key.replaceAll("-----BEGIN[^-\\r\\n]*-----", "")
                    .replaceAll("-----END[^-\\r\\n]*-----", "")
                    .replaceAll("\\s", "")
                    .trim();

            byte[] decoded = java.util.Base64.getDecoder().decode(key);

            return KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        }
    }

    public PublicKey loadPublicKey() throws Exception {
        try (var inputStream = resourceLoader.getResource(properties.privateKeyPath()).getInputStream()) {
            String key = new String(inputStream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);

            key = key.replaceAll("-----BEGIN[^-\\r\\n]*-----", "")
                    .replaceAll("-----END[^-\\r\\n]*-----", "")
                    .replaceAll("\\s", "")
                    .trim();

            byte[] decoded = java.util.Base64.getDecoder().decode(key);

            return KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
        }
    }
}