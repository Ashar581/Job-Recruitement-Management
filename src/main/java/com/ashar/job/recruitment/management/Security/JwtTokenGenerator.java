package com.ashar.job.recruitment.management.Security;

import com.ashar.job.recruitment.management.Entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenGenerator {
    @Autowired
    private KeyConfig keyConfig;

    public String generateToken(User user){
        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRoleCode())
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(user.getName())
                .claims(Map.of("roles",roles))
                .setId(user.getUuid().toString())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now()
                        .plusMinutes(keyConfig.getRtexpirationmins())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(keyConfig.getKey())
                .compact();
    }
}
