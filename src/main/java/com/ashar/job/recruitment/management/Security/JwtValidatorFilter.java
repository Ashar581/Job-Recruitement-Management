package com.ashar.job.recruitment.management.Security;

import com.ashar.job.recruitment.management.Exception.VerificationFailedException;
import com.ashar.job.recruitment.management.Security.LocalStoreAuthority.AuthenticatedContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtValidatorFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenValidator jwtTokenValidator;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("Authorization")!=null){
            String token = request.getHeader("Authorization").replace("Bearer ","").trim();

            if (jwtTokenValidator.isValid(token)) throw new VerificationFailedException("The Token Is Expired");
            List<String> roles = jwtTokenValidator.getAuthorities(token);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());

            //Storing required details in ThreadLocal
            AuthenticatedContext authenticatedContext = new AuthenticatedContext(roles, UUID.fromString(jwtTokenValidator.getUuid(token)),token);
            AuthenticatedContext.setAuthenticatedContext(authenticatedContext);
            //Understand the concept here. We basically use the SecurityContextHolder to store the authenticated user.
            //Now, there is also a section where we have to store an ArrayList.
            //Here if we have a ROLE and AUTHORITY based application, then we store a List of GrantedAuthority.
            //This would basically make sure that the program can use functions such as .hasAuthority() and .hasRole()
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(jwtTokenValidator.getUuid(token), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}