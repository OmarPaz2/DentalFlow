package com.dentalflow.auth.service.api.controllers;

import com.dentalflow.auth.service.security.RsaKeyLoader;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Hidden
public class JwkSetController {

    private final RsaKeyLoader rsaKeyLoader;

    @GetMapping("/api/auth/.well-known/jwks.json")
    public Map<String, Object> getJwkSet() throws Exception {

        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) rsaKeyLoader.loadPublicKey())
                .keyID("dentalflow-auth-key")
                .build();

        return Map.of(
                "keys",
                new Object[]{rsaKey.toPublicJWK().toJSONObject()}
        );
    }
}