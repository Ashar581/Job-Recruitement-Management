package com.ashar.job.recruitment.management.Security.LocalStoreAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedContext {
    public static final ThreadLocal<AuthenticatedContext> authenticatedContext= new ThreadLocal<>();

    private List<String> roles;
    private UUID email;
    private String token;
    public UUID getEmail() {
        return email;
    }
    public void setEmail(UUID email) {
        this.email = email;
    }
    public List<String> getRoles(){
        return this.roles;
    }
    public void setRoles(List<SimpleGrantedAuthority> roles){
        this.roles = roles.stream()
                .map(role -> role.getAuthority().toString())
                .collect(Collectors.toList());
    }
    public String getToken(){
        return this.token;
    }
    public void setToken(String token){
        this.token = token;
    }

    public static void setAuthenticatedContext(AuthenticatedContext context){
        authenticatedContext.set(context);
    }
    public static AuthenticatedContext getAuthenticatedContext(){
        return authenticatedContext.get();
    }
    public static void clear(){
        authenticatedContext.remove();
    }
}
