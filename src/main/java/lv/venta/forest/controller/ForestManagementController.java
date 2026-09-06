package lv.venta.forest.controller;

import lv.venta.forest.model.ForestManagementPractice;
import lv.venta.forest.model.TreeSpecies;
import lv.venta.forest.model.WaterBalance;
import lv.venta.forest.repo.ForestManagementPracticeRepository;
import lv.venta.forest.repo.TreeSpeciesRepository;
import lv.venta.forest.repo.WaterBalanceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forest")
public class ForestManagementController {
    private final TreeSpeciesRepository treeSpeciesRepository;
    private final ForestManagementPracticeRepository practiceRepository;
    private final WaterBalanceRepository waterBalanceRepository;

    public ForestManagementController(TreeSpeciesRepository treeSpeciesRepository,
                                      ForestManagementPracticeRepository practiceRepository,
                                      WaterBalanceRepository waterBalanceRepository) {
        this.treeSpeciesRepository = treeSpeciesRepository;
        this.practiceRepository = practiceRepository;
        this.waterBalanceRepository = waterBalanceRepository;
    }

    @GetMapping("/treespecies")
    public List<TreeSpecies> getTreeSpecies() { return treeSpeciesRepository.findAll(); }

    @GetMapping("/treespecies/dominant")
    public List<TreeSpecies> getDominantTreeSpecies() { return treeSpeciesRepository.findByIsDominantTrue(); }

    @GetMapping("/treespecies/recommend")
    public List<TreeSpecies> recommendTreeSpecies(@RequestParam String soilType) {
        return treeSpeciesRepository.findBySoilType(soilType);
    }

    @GetMapping("/practices")
    public List<ForestManagementPractice> getPractices() { return practiceRepository.findAll(); }

    @PostMapping("/practices")
    public ForestManagementPractice createPractice(@RequestBody ForestManagementPractice practice) {
        return practiceRepository.save(practice);
    }

    @GetMapping("/waterbalance/zone/{zoneId}")
    public List<WaterBalance> getWaterBalance(@PathVariable Long zoneId) {
        return waterBalanceRepository.findByForestZoneId(zoneId);
    }

    @PostMapping("/waterbalance")
    public WaterBalance saveWaterBalance(@RequestBody WaterBalance balance) {
        double inputs = value(balance.getPrecipitation()) + value(balance.getIrrigation()) + value(balance.getGroundwaterContribution());
        double outputs = value(balance.getEvapotranspiration()) + value(balance.getRunoff()) + value(balance.getDeepPercolation());
        double soilMoisture = value(balance.getSoilMoistureContent());
        double fieldCapacity = value(balance.getFieldCapacity());
        double wiltingPoint = value(balance.getWiltingPoint());
        balance.setWaterBalance(inputs - outputs);
        balance.setAvailableWater(soilMoisture - wiltingPoint);
        balance.setWaterDeficit(Math.max(0.0, fieldCapacity - soilMoisture));
        return waterBalanceRepository.save(balance);
    }

    @GetMapping("/water/calculate")
    public Map<String, Object> calculateWater(@RequestParam double area, @RequestParam int duration) {
        if (area < 0 || duration < 0) throw new IllegalArgumentException("Area and duration must not be negative");
        double liters = area * duration;
        Map<String, Object> result = new HashMap<>();
        result.put("area", area);
        result.put("durationMinutes", duration);
        result.put("waterNeededLiters", liters);
        result.put("estimatedCostEUR", liters * 0.002);
        result.put("algorithm", "1 L per square metre per minute");
        return result;
    }

    private double value(Double number) { return number == null ? 0.0 : number; }
}
