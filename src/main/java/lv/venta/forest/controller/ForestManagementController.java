package lv.venta.forest.controller;

import lv.venta.forest.model.*;
import lv.venta.forest.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/forest")
public class ForestManagementController {
    
    @Autowired
    private TreeSpeciesRepository treeSpeciesRepo;
    
    @Autowired
    private ForestManagementPracticeRepository practiceRepo;
    
    @Autowired
    private WaterBalanceRepository waterBalanceRepo;
    
    @Autowired
    private ForestZoneRepository zoneRepo;
    
    // Tree Species endpoints
    @GetMapping("/treespecies")
    public List<TreeSpecies> getAllTreeSpecies() {
        return treeSpeciesRepo.findAll();
    }
    
    @GetMapping("/treespecies/dominant")
    public List<TreeSpecies> getDominantTreeSpecies() {
        return treeSpeciesRepo.findByIsDominantTrue();
    }
    
    @GetMapping("/treespecies/recommend")
    public List<TreeSpecies> recommendTreeSpecies(@RequestParam String soilType) {
        return treeSpeciesRepo.findBySoilType(soilType);
    }
    
    // Forest Management Practice endpoints
    @GetMapping("/practices")
    public List<ForestManagementPractice> getAllPractices() {
        return practiceRepo.findAll();
    }
    
    @PostMapping("/practices")
    public ForestManagementPractice createPractice(@RequestBody ForestManagementPractice practice) {
        return practiceRepo.save(practice);
    }
    
    // Water Balance endpoints
    @GetMapping("/waterbalance/zone/{zoneId}")
    public List<WaterBalance> getWaterBalanceByZone(@PathVariable Long zoneId) {
        return waterBalanceRepo.findByForestZoneId(zoneId);
    }
    
    @PostMapping("/waterbalance")
    public WaterBalance calculateWaterBalance(@RequestBody WaterBalance waterBalance) {
        // Calculate water balance: inputs - outputs
        double inputs = waterBalance.getPrecipitation() + waterBalance.getIrrigation() + waterBalance.getGroundwaterContribution();
        double outputs = waterBalance.getEvapotranspiration() + waterBalance.getRunoff() + waterBalance.getDeepPercolation();
        waterBalance.setWaterBalance(inputs - outputs);
        
        // Calculate available water and deficit
        waterBalance.setAvailableWater(waterBalance.getSoilMoistureContent() - waterBalance.getWiltingPoint());
        waterBalance.setWaterDeficit(waterBalance.getFieldCapacity() - waterBalance.getSoilMoistureContent());
        
        return waterBalanceRepo.save(waterBalance);
    }
    
    // Water saving calculation endpoint
    @GetMapping("/water/calculate")
    public Map<String, Object> calculateWaterNeeds(
            @RequestParam Double area, // square meters
            @RequestParam Integer duration) { // minutes
        
        Map<String, Object> result = new HashMap<>();
        
        // Algorithm: 1L per 1m2 per 1 minute
        double waterNeeded = area * duration; // liters
        double costEstimate = waterNeeded * 0.002; // assuming 0.002 EUR per liter
        
        result.put("area", area);
        result.put("durationMinutes", duration);
        result.put("waterNeededLiters", waterNeeded);
        result.put("estimatedCostEUR", costEstimate);
        result.put("algorithm", "1L per 1m2 per 1 minute");
        
        return result;
    }
}
