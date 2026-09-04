package lv.venta.forest.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TreeSpecies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String scientificName;
    private String type; // deciduous, coniferous, mixed
    
    // Growth characteristics
    private Double growthRate; // meters per year
    private Double matureHeight; // meters
    private Double lifespan; // years
    
    // Environmental requirements
    private String soilType; // sandy, clay, loamy, etc.
    private Integer minTemperature; // celsius
    private Integer maxTemperature; // celsius
    private Double annualRainfall; // mm
    
    // Water balance properties
    private Double waterRequirement; // liters per m2 per day
    private Double droughtTolerance; // 0-10 scale
    private Double waterRetention; // soil water retention capacity
    
    // Dominant tree indicator
    private Boolean isDominant; // true if dominant species in area
    private Double dominancePercentage; // percentage of forest coverage
    
    // Management practices
    @OneToMany(mappedBy = "treeSpecies", cascade = CascadeType.ALL)
    private List<ForestManagementPractice> managementPractices = new ArrayList<>();
    
    // Constructors, getters, setters
    public TreeSpecies() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getGrowthRate() { return growthRate; }
    public void setGrowthRate(Double growthRate) { this.growthRate = growthRate; }
    public Double getMatureHeight() { return matureHeight; }
    public void setMatureHeight(Double matureHeight) { this.matureHeight = matureHeight; }
    public Double getLifespan() { return lifespan; }
    public void setLifespan(Double lifespan) { this.lifespan = lifespan; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public Integer getMinTemperature() { return minTemperature; }
    public void setMinTemperature(Integer minTemperature) { this.minTemperature = minTemperature; }
    public Integer getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(Integer maxTemperature) { this.maxTemperature = maxTemperature; }
    public Double getAnnualRainfall() { return annualRainfall; }
    public void setAnnualRainfall(Double annualRainfall) { this.annualRainfall = annualRainfall; }
    public Double getWaterRequirement() { return waterRequirement; }
    public void setWaterRequirement(Double waterRequirement) { this.waterRequirement = waterRequirement; }
    public Double getDroughtTolerance() { return droughtTolerance; }
    public void setDroughtTolerance(Double droughtTolerance) { this.droughtTolerance = droughtTolerance; }
    public Double getWaterRetention() { return waterRetention; }
    public void setWaterRetention(Double waterRetention) { this.waterRetention = waterRetention; }
    public Boolean getIsDominant() { return isDominant; }
    public void setIsDominant(Boolean isDominant) { this.isDominant = isDominant; }
    public Double getDominancePercentage() { return dominancePercentage; }
    public void setDominancePercentage(Double dominancePercentage) { this.dominancePercentage = dominancePercentage; }
    public List<ForestManagementPractice> getManagementPractices() { return managementPractices; }
    public void setManagementPractices(List<ForestManagementPractice> managementPractices) { this.managementPractices = managementPractices; }
}
