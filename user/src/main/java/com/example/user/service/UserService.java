package com.example.user.service;

import com.example.user.dto.FinalResponseDataDto;
import com.example.user.dto.UserLoginDto;
import com.example.user.dto.UserRegisterDto;
import com.example.user.dto.ResponseDataDto;
import com.example.user.entity.Role;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final String secretKey;

    public UserService(UserRepository userRepository, @Value("${jwt.private}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    public List<FinalResponseDataDto> mapAllUsersWithPayments(List<ResponseDataDto> paymentsList) {
        List<Long> idList = new ArrayList<>();
        for (ResponseDataDto payment : paymentsList) {
            idList.add(payment.getSenderId());
        }
        List<User> userListById = userRepository.findUsersWithListId(idList);

        List<FinalResponseDataDto> finalList = new ArrayList<>();

        for (ResponseDataDto payment : paymentsList) {
            for (User user : userListById) {
                if (payment.getSenderId().equals(user.getId()))
                    finalList.add(new FinalResponseDataDto(payment.getId(), payment.getSenderId(), payment.getAmount(),
                            payment.getComment(), payment.getCardNumber(), user.getUsername(), user.getRole()));
            }
        }
        return finalList;
    }


    public User saveUser(UserRegisterDto userRegisterDto) {
        final Optional<User> maybeUser = userRepository.findUserByUsername(userRegisterDto.getUsername());
        if (maybeUser.isEmpty()) {
            User user = new User();
            user.setUsername(userRegisterDto.getUsername());
            user.setPassword(userRegisterDto.getPassword());
            user.setRole(Role.ROLE_USER.name());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findUserByUsernameAndPassword(userLoginDto.getUsername(), userLoginDto.getPassword()).orElseThrow(RuntimeException::new);
        return generateToken(user);
    }

    @SneakyThrows
    private String generateToken(User savedUser) {
        final var keyFactory = KeyFactory.getInstance("RSA");
        final var privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(secretKey
                .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
        )));
        final JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .expirationTime(Date.from(Instant.now().plus(10, ChronoUnit.HOURS)))
                .issueTime(new java.util.Date())
                .claim("id", savedUser.getId())
                .claim("role", savedUser.getRole())
                .build();


        final var signer = new RSASSASigner(privateKey);
        final var signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}


