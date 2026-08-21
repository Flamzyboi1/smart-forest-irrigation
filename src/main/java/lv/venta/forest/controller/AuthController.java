package lv.venta.forest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import lv.venta.forest.config.JwtUtil;
import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository users;
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            AppUserRepository users,
            JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.users = users;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body) {

        String username = body.getOrDefault("username", "").trim();
        String password = body.getOrDefault("password", "");

        if (username.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Username and password are required"
                    ));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

        } catch (AuthenticationException e) {

            return ResponseEntity
                    .status(401)
                    .body(Map.of(
                            "error",
                            "Invalid username or password"
                    ));
        }

        AppUser user = users.findByUsername(username)
                .orElseThrow();

        String token = jwtUtil.generateToken(username);

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "username", user.getUsername(),
                        "fullName", user.getFullName(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                )
        );
    }
}