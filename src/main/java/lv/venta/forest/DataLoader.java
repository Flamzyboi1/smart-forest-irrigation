package lv.venta.forest.model;

import lv.venta.forest.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private AppUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TreeSpeciesRepository treeSpeciesRepository;
    
    @Autowired
    private ForestManagementPracticeRepository practiceRepository;
    
    @Autowired
    private WaterBalanceRepository waterBalanceRepository;
    
    @Autowired
    private ForestZoneRepository zoneRepository;
    
    @Override
    public void run(String... args) {
        // Create admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        
        // Create sample forest zones
        ForestZone zone1 = new ForestZone();
        zone1.setName("Northern Forest Zone");
        zone1.setArea(5000.0); // 5000 m2
        zone1.setLocation("Ventspils region");
        zone1.setSoilType("loamy");
        zoneRepository.save(zone1);
        
        // Create tree species (dominant trees for Latvia/Baltic region)
        TreeSpecies pine = new TreeSpecies();
        pine.setName("Scots Pine");
        pine.setScientificName("Pinus sylvestris");
        pine.setType("coniferous");
        pine.setGrowthRate(0.5);
        pine.setMatureHeight(35.0);
        pine.setLifespan(200.0);
        pine.setSoilType("sandy");
        pine.setMinTemperature(-30);
        pine.setMaxTemperature(35);
        pine.setAnnualRainfall(600.0);
        pine.setWaterRequirement(2.5);
        pine.setDroughtTolerance(8.0);
        pine.setWaterRetention(6.5);
        pine.setIsDominant(true);
        pine.setDominancePercentage(45.0);
        treeSpeciesRepository.save(pine);
        
        TreeSpecies spruce = new TreeSpecies();
        spruce.setName("Norway Spruce");
        spruce.setScientificName("Picea abies");
        spruce.setType("coniferous");
        spruce.setGrowthRate(0.4);
        spruce.setMatureHeight(40.0);
        spruce.setLifespan(250.0);
        spruce.setSoilType("loamy");
        spruce.setMinTemperature(-35);
        spruce.setMaxTemperature(30);
        spruce.setAnnualRainfall(700.0);
        spruce.setWaterRequirement(3.0);
        spruce.setDroughtTolerance(6.0);
        spruce.setWaterRetention(7.0);
        spruce.setIsDominant(true);
        spruce.setDominancePercentage(35.0);
        treeSpeciesRepository.save(spruce);
        
        TreeSpecies birch = new TreeSpecies();
        birch.setName("Silver Birch");
        birch.setScientificName("Betula pendula");
        birch.setType("deciduous");
        birch.setGrowthRate(0.6);
        birch.setMatureHeight(25.0);
        birch.setLifespan(100.0);
        birch.setSoilType("sandy");
        birch.setMinTemperature(-40);
        birch.setMaxTemperature(35);
        birch.setAnnualRainfall(500.0);
        birch.setWaterRequirement(2.0);
        birch.setDroughtTolerance(7.0);
        birch.setWaterRetention(5.5);
        birch.setIsDominant(false);
        birch.setDominancePercentage(15.0);
        treeSpeciesRepository.save(birch);
        
        // Create forest management practices
        ForestManagementPractice planting = new ForestManagementPractice();
        planting.setPracticeType("planting");
        planting.setDescription("Spring planting of Scots Pine seedlings");
        planting.setScheduledDate(LocalDate.of(2026, 5, 15));
        planting.setStatus("planned");
        planting.setEstimatedCost(1500.0);
        planting.setAreaCovered(2000.0);
        planting.setTreeSpecies(pine);
        planting.setForestZone(zone1);
        practiceRepository.save(planting);
        
        ForestManagementPractice thinning = new ForestManagementPractice();
        thinning.setPracticeType("thinning");
        thinning.setDescription("Selective thinning to improve growth");
        thinning.setScheduledDate(LocalDate.of(2026, 9, 1));
        thinning.setStatus("planned");
        thinning.setEstimatedCost(800.0);
        thinning.setWaterSaved(5000.0);
        thinning.setAreaCovered(1500.0);
        thinning.setTreeSpecies(spruce);
        thinning.setForestZone(zone1);
        practiceRepository.save(thinning);
        
        // Create water balance record
        WaterBalance wb = new WaterBalance();
        wb.setForestZone(zone1);
        wb.setMeasurementDate(LocalDate.now());
        wb.setPrecipitation(5.0); // mm
        wb.setIrrigation(0.0);
        wb.setGroundwaterContribution(1.0);
        wb.setEvapotranspiration(3.5);
        wb.setRunoff(0.5);
        wb.setDeepPercolation(1.0);
        wb.setSoilMoistureContent(25.0); // %
        wb.setFieldCapacity(35.0);
        wb.setWiltingPoint(12.0);
        // Calculate water balance
        double inputs = wb.getPrecipitation() + wb.getIrrigation() + wb.getGroundwaterContribution();
        double outputs = wb.getEvapotranspiration() + wb.getRunoff() + wb.getDeepPercolation();
        wb.setWaterBalance(inputs - outputs);
        wb.setAvailableWater(wb.getSoilMoistureContent() - wb.getWiltingPoint());
        wb.setWaterDeficit(wb.getFieldCapacity() - wb.getSoilMoistureContent());
        waterBalanceRepository.save(wb);
    }
}
