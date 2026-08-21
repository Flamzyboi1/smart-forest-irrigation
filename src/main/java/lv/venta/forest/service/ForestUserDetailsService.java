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
    @Override public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        AppUser u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole()).disabled(!u.isActive()).build();
    }
}
