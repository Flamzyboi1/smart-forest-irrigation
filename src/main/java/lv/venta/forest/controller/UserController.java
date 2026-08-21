package lv.venta.forest.controller;

import lv.venta.forest.model.*;
import lv.venta.forest.repo.*;
import lv.venta.forest.service.ForestService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final AppUserRepository users;
    private final UserChangeRequestRepository requests;
    private final PasswordEncoder encoder;
    private final ForestService service;
    public UserController(AppUserRepository users,UserChangeRequestRepository requests,PasswordEncoder encoder,ForestService service){this.users=users;this.requests=requests;this.encoder=encoder;this.service=service;}

    @GetMapping("/me")
    public ResponseEntity<AppUser> me(Authentication auth){
        if(auth==null||!auth.isAuthenticated()) return ResponseEntity.status(401).build();
        return users.findByUsername(auth.getName()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me/requests")
    public ResponseEntity<List<UserChangeRequest>> myRequests(Authentication auth){
        AppUser u=users.findByUsername(auth.getName()).orElseThrow();
        return ResponseEntity.ok(requests.findByUserIdOrderByRequestedAtDesc(u.getId()));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping
    public List<AppUser> all(){ return service.getAllUsers(); }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AppUser input){
        if(users.existsByUsername(input.getUsername())) return ResponseEntity.badRequest().body(Map.of("error","Username already exists"));
        if(users.existsByEmail(input.getEmail())) return ResponseEntity.badRequest().body(Map.of("error","Email already exists"));
        input.setPassword(encoder.encode(input.getPassword())); input.setActive(true);
        return ResponseEntity.ok(users.save(input));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody AppUser input){
        return users.findById(id).map(u->{
            if(!u.getUsername().equals(input.getUsername()) && users.existsByUsername(input.getUsername())) return ResponseEntity.badRequest().body(Map.of("error","Username already exists"));
            u.setUsername(input.getUsername());u.setFullName(input.getFullName());u.setEmail(input.getEmail());u.setRole(input.getRole());u.setActive(input.isActive());
            if(input.getPassword()!=null&&!input.getPassword().isBlank()) u.setPassword(encoder.encode(input.getPassword()));
            return ResponseEntity.ok(users.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,Authentication auth){
        AppUser me=users.findByUsername(auth.getName()).orElseThrow();
        if(me.getId().equals(id)) return ResponseEntity.badRequest().body(Map.of("error","You cannot delete your own account"));
        if(!users.existsById(id)) return ResponseEntity.notFound().build();
        users.deleteById(id); return ResponseEntity.noContent().build();
    }

    @PostMapping("/me/request")
    public ResponseEntity<?> requestChange(@RequestBody Map<String,String> body,Authentication auth){
        AppUser u=users.findByUsername(auth.getName()).orElseThrow();
        if(requests.existsByUserIdAndStatus(u.getId(),"PENDING")) return ResponseEntity.badRequest().body(Map.of("error","You already have a pending request"));
        String username=body.getOrDefault("username",u.getUsername()).trim();
        String fullName=body.getOrDefault("fullName",u.getFullName()).trim();
        String email=body.getOrDefault("email",u.getEmail()).trim();
        if(!username.matches("^[A-Za-z0-9._-]{3,30}$")) return ResponseEntity.badRequest().body(Map.of("error","Invalid username format"));
        if(!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return ResponseEntity.badRequest().body(Map.of("error","Invalid email"));
        if(!username.equals(u.getUsername()) && users.existsByUsername(username)) return ResponseEntity.badRequest().body(Map.of("error","Username already exists"));
        if(!email.equals(u.getEmail()) && users.existsByEmail(email)) return ResponseEntity.badRequest().body(Map.of("error","Email already exists"));
        UserChangeRequest r=new UserChangeRequest();r.setUser(u);r.setRequestedUsername(username);r.setRequestedFullName(fullName);r.setRequestedEmail(email);String requestedPassword=body.get("password");r.setRequestedPassword(requestedPassword!=null&&!requestedPassword.isBlank()?encoder.encode(requestedPassword):null);
        return ResponseEntity.ok(requests.save(r));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/pending")
    public List<UserChangeRequest> pending(){ return requests.findByStatusOrderByRequestedAtDesc("PENDING"); }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping("/requests/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id,Authentication auth){
        return requests.findById(id).map(r->{
            if(!"PENDING".equals(r.getStatus())) return ResponseEntity.badRequest().body(Map.of("error","Request already reviewed"));
            AppUser u=r.getUser(); u.setUsername(r.getRequestedUsername());u.setFullName(r.getRequestedFullName());u.setEmail(r.getRequestedEmail());
            if(r.getRequestedPassword()!=null&&!r.getRequestedPassword().isBlank()) u.setPassword(r.getRequestedPassword());
            users.save(u);r.setStatus("APPROVED");r.setReviewedAt(LocalDateTime.now());r.setReviewedBy(auth.getName());requests.save(r);
            return ResponseEntity.ok(r);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping("/requests/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id,Authentication auth){
        return requests.findById(id).map(r->{if(!"PENDING".equals(r.getStatus())) return ResponseEntity.badRequest().body(Map.of("error","Request already reviewed"));r.setStatus("REJECTED");r.setReviewedAt(LocalDateTime.now());r.setReviewedBy(auth.getName());return ResponseEntity.ok(requests.save(r));}).orElse(ResponseEntity.notFound().build());
    }
}
