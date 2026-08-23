package lv.venta.forest.controller;

import lv.venta.forest.config.JwtUtil;
import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository users;
    private final JwtUtil jwt;
    public AuthController(AuthenticationManager authenticationManager, AppUserRepository users, JwtUtil jwt){this.authenticationManager=authenticationManager;this.users=users;this.jwt=jwt;}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        AppUser u=users.findByUsernameIgnoreCase(request.username()).orElseThrow();
        return ResponseEntity.ok(Map.of("token",jwt.generateToken(u.getUsername()),"username",u.getUsername(),"fullName",u.getFullName(),"role",u.getRole().name(),"status",u.getStatus().name()));
    }
    public record LoginRequest(String username,String password){}
}
