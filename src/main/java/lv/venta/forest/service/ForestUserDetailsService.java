package lv.venta.forest.service;

import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ForestUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;

    public ForestUserDetailsService(AppUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        String role = user.getRole() == AppUser.Role.SUPERADMIN ? "SUPERADMIN" : user.getRole().name();
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + role))
                .disabled(user.getStatus() != AppUser.Status.ACTIVE)
                .build();
    }
}
