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

    public DataLoader(AppUserRepository userRepo,
                      ForestZoneRepository zoneRepo,
                      ForestSensorRepository sensorRepo,
                      SensorReadingRepository readingRepo,
                      ForestAlertRepository alertRepo,
                      PasswordEncoder encoder) {
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

            AppUser manager = new AppUser("manager", encoder.encode("manager123"), "MANAGER");
            manager.setFullName("Forest Manager");
            manager.setEmail("manager@forest.local");
            userRepo.save(manager);

            AppUser user = new AppUser("user", encoder.encode("user123"), "USER");
            user.setFullName("Forest Worker");
            user.setEmail("user@forest.local");
            userRepo.save(user);
        }

        if (zoneRepo.count() == 0) {
            ForestZone zone1 = new ForestZone("North Forest", "Ventspils, Latvia", 12.5, "Pine forest");
            ForestZone zone2 = new ForestZone("River Side", "Venta River area", 8.0, "Mixed forest");
            zoneRepo.save(zone1);
            zoneRepo.save(zone2);

            ForestSensor sensor1 = new ForestSensor("SENSOR-001", "Soil Moisture", "soil_moisture", zone1);
            ForestSensor sensor2 = new ForestSensor("SENSOR-002", "Temperature", "temperature", zone1);
            ForestSensor sensor3 = new ForestSensor("SENSOR-003", "Humidity", "humidity", zone2);
            sensorRepo.save(sensor1);
            sensorRepo.save(sensor2);
            sensorRepo.save(sensor3);

            readingRepo.save(new SensorReading(sensor1, 32.5, "%"));
            readingRepo.save(new SensorReading(sensor2, 18.7, "°C"));
            readingRepo.save(new SensorReading(sensor3, 71.0, "%"));

            alertRepo.save(new ForestAlert("LOW_MOISTURE", "Soil moisture is below the configured threshold", "HIGH", zone1));
            alertRepo.save(new ForestAlert("SENSOR_CHECK", "Check sensor battery status", "MEDIUM", zone2));
        }
    }
}
