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
        String key = Files.readString(
                resourceLoader.getResource(properties.privateKeyPath())
                        .getFile()
                        .toPath()
        );

        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = java.util.Base64.getDecoder().decode(key);

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public PublicKey loadPublicKey() throws Exception {
        String key = Files.readString(
                resourceLoader.getResource(properties.publicKeyPath())
                        .getFile()
                        .toPath()
        );

        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = java.util.Base64.getDecoder().decode(key);

        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }
}