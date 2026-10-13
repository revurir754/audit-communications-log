package com.example.auditlog.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoUserDetailsService implements UserDetailsService {

    private final String demoUsername = "demo";
    private final String demoPasswordHash;

    public DemoUserDetailsService(@Lazy org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        // encode demo password for in-memory demo user
        this.demoPasswordHash = passwordEncoder.encode("demo-password");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (demoUsername.equals(username)) {
            return new User(demoUsername, demoPasswordHash, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
