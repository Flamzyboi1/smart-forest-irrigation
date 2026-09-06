package lv.venta.forest;

import lv.venta.forest.model.*;
import lv.venta.forest.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final AppUserRepository userRepo;
    private final ForestZoneRepository zoneRepo;
    private final ForestSensorRepository sensorRepo;
    private final SensorReadingRepository readingRepo;
    private final ForestAlertRepository alertRepo;
    private final PasswordEncoder encoder;

    public DataLoader(AppUserRepository userRepo, ForestZoneRepository zoneRepo,
                      ForestSensorRepository sensorRepo, SensorReadingRepository readingRepo,
                      ForestAlertRepository alertRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.zoneRepo = zoneRepo;
        this.sensorRepo = sensorRepo;
        this.readingRepo = readingRepo;
        this.alertRepo = alertRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            AppUser admin = new AppUser("admin", encoder.encode("admin123"), "ADMIN");
            admin.setFullName("System Administrator");
            admin.setEmail("admin@forest.local");
            userRepo.save(admin);
        }

        if (zoneRepo.count() == 0) {
            ForestZone zone = new ForestZone("North Forest", "Ventspils, Latvia", 12.5, "Pine forest");
            zoneRepo.save(zone);

            ForestSensor moisture = new ForestSensor("SENSOR-001", "Soil Moisture", "soil_moisture", zone);
            ForestSensor temperature = new ForestSensor("SENSOR-002", "Temperature", "temperature", zone);
            sensorRepo.save(moisture);
            sensorRepo.save(temperature);

            readingRepo.save(new SensorReading(moisture, 32.5, "%"));
            readingRepo.save(new SensorReading(temperature, 18.7, "C"));
            alertRepo.save(new ForestAlert("LOW_MOISTURE", "Soil moisture is below the configured threshold", "HIGH", zone));
        }
    }
}
