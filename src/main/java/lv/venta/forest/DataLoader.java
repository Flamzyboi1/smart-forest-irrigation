package lv.venta.forest;

import lv.venta.forest.model.AppUser;
import lv.venta.forest.repo.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final AppUserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataLoader(AppUserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.findByUsernameIgnoreCase("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setEmail("admin@forest.local");
            admin.setRole(AppUser.Role.SUPERADMIN);
            admin.setStatus(AppUser.Status.ACTIVE);
            admin.setActive(true);
            userRepo.save(admin);
        }
    }
}
