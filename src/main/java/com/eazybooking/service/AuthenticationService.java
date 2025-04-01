package com.eazybooking.service;

import com.eazybooking.dto.AuthRequest;
import com.eazybooking.dto.AuthResponse;
import com.eazybooking.dto.RegisterRequest;
import com.eazybooking.model.CustomUserDetails;
import com.eazybooking.model.User;
import com.eazybooking.repository.UserRepository;
import com.eazybooking.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eazybooking.model.Role;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class); // ✅ Manually added logger

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider,
                                 AuthenticationManager authenticationManager,
                                 UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // ✅ Hashing password
        user.setRole("ROLE_USER"); // ✅ Assigning role as a String

        userRepository.save(user);
        log.info("✅ New user registered: {}", user.getEmail());

        // ✅ Generate JWT token
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(user));

        return new AuthResponse(token, "Registration successful", 200);
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            log.info("🔹 Attempting to authenticate user: {}", request.getEmail());

            // ✅ Fetch user details first
            User userDetails = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (userDetails == null) {
                return AuthResponse.builder()
                        .status(404)
                        .message("Invalid username or password")
                        .build();
            }

            // ✅ Authenticate the user
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                log.info("✅ User authenticated successfully: {}", request.getEmail());

                // ✅ Generate JWT token
                String token = jwtTokenProvider.generateToken(new CustomUserDetails(userDetails));

                return new AuthResponse(token, "Login successful", 200);
            }

            return new AuthResponse(null, "Invalid username or password", 401);

        } catch (Exception e) {
            log.error("❌ Unable to complete login: {}", e.getMessage());
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
