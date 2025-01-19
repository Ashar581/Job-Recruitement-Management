package com.ashar.job.recruitment.management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Data
public class JwtTokenValidator {
    @Autowired
    private KeyConfig keyConfig;

    public boolean isValid(String token){
        return isExpired(token);
    }
    public String getUuid(String token){
        return getJws(token)
                .getBody()
                .getId();
    }
    public List<String> getAuthorities(String token){
        return (List<String>) getJws(token)
                .getBody()
                .get("roles");

    }
    private boolean isExpired(String token){
        return getJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
    private Jws<Claims> getJws(String token){
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(keyConfig.getKey())
                .build()
                .parseClaimsJws(token);
        return jws;
    }

}
