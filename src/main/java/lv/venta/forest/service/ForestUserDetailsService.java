package lv.venta.forest.service;

import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ForestUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;
    public ForestUserDetailsService(AppUserRepository repo){this.repo=repo;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u=repo.findByUsernameIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException(username));
        String role=u.getRole()==AppUser.Role.SUPERADMIN?"SUPERADMIN":u.getRole().name();
        return User.withUsername(u.getUsername()).password(u.getPassword()).authorities(new SimpleGrantedAuthority("ROLE_"+role)).disabled(u.getStatus()!=AppUser.Status.ACTIVE).build();
    }
}
