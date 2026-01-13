package com.example.JWT.service;

import com.example.JWT.model.Role;
import com.example.JWT.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final Map<String, User> users = new HashMap<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService() {
        createUser("admin", "admin123", "admin@example.com", Role.ADMIN);
        createUser("user", "user123", "user@example.com", Role.USER);
        createUser("manager", "manager123", "manager@example.com", Role.MANAGER);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }

    public User createUser(String username, String password, String email, Role role) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, username, passwordEncoder.encode(password), email, role);
        users.put(id, user);
        return user;
    }

    public User findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User findById(String id) {
        return users.get(id);
    }
}