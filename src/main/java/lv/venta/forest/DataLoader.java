package lv.venta.forest;

import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner seedUsers(AppUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            AppUser admin = repo.findByUsernameIgnoreCase("ForestAdmin").orElseGet(AppUser::new);
            admin.setUsername("ForestAdmin");
            admin.setFullName("Forest Administrator");
            admin.setEmail("admin@forest.lv");
            admin.setRole(AppUser.Role.SUPERADMIN);
            admin.setStatus(AppUser.Status.ACTIVE);
            if (admin.getPassword() == null || admin.getPassword().isBlank()) admin.setPassword(encoder.encode("ForestAdmin123!"));
            repo.save(admin);
        };
    }
}
