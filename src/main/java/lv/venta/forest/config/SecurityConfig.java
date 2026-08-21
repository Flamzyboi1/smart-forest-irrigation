package lv.venta.forest.config;

import lv.venta.forest.repo.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AppUserRepository appUserRepository;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AppUserRepository appUserRepository){this.jwtAuthenticationFilter=jwtAuthenticationFilter;this.appUserRepository=appUserRepository;}
    @Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean public UserDetailsService userDetailsService(){return username->appUserRepository.findByUsername(username).map(u->User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().name().replace("ROLE_","")).disabled(!u.isActive()).build()).orElseThrow(()->new UsernameNotFoundException("User not found"));}
    @Bean public DaoAuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder encoder){DaoAuthenticationProvider p=new DaoAuthenticationProvider();p.setUserDetailsService(uds);p.setPasswordEncoder(encoder);return p;}
    @Bean public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider){return new ProviderManager(provider);}
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider provider)throws Exception{http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(provider).authorizeHttpRequests().requestMatchers("/","/*.html","/js/**","/css/**","/images/**","/favicon.ico","/api/auth/**").permitAll().requestMatchers("/api/users/**").hasRole("SUPERADMIN").anyRequest().authenticated().and().addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);return http.build();}
}
