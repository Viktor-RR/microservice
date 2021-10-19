package com.example.gateway.service;


import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class AuthenticateService  {

    private final String publicKey;

    public AuthenticateService(@Value("${jwt.public}") String publicKey) {
        this.publicKey = publicKey;
    }

    @SneakyThrows
    public String getData(SignedJWT token) {
        final Map<String, Object> stringObjectMap = token.getPayload().toJSONObject();

        final Map<String, Object> finalMap = Map.of(
                "id", stringObjectMap.get("id"),
                "role", stringObjectMap.get("role")
        );
        final String data = new JSONObject(finalMap).toJSONString();
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public void checkTokenExpirationDate(SignedJWT parsedToken) {
        final Map<String, Object> stringObjectMap = parsedToken.getPayload().toJSONObject();
        final long exp = (Long) stringObjectMap.get("exp");
        if (System.currentTimeMillis() / 1000 > exp)
            throw new RuntimeException("Token is expired");
    }

    public RSAPublicKey getRsaPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        final var keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
        )));
    }
}
